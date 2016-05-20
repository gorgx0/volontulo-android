package com.stxnext.volontulo.logic.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.api.LoginResponse;
import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.logic.im.config.ImConfigFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Manages user session state.
 */
public class SessionManager {

    /**
     * Session state change callback.
     */
    public interface OnSessionStateChanged {
        /**
         * Called when user session changes state, ie. user logged in or logged out.
         * @param session new session state.
         */
        void onSessionStateChanged(Session session);
    }

    private SharedPreferences preferences;
    private volatile Session session;
    private Realm realm;
    private final List<OnSessionStateChanged> listeners;

    private static final String PREF_USER_PROFILE_ID = "PREF-USER-PROFILE-ID";
    private static final String PREF_SESSION_AUTH = "PREF-USER-IS-LOGGED";
    private static final String PREF_SESSION_KEY = "PREF-USER-KEY";

    private static final Object LOCK = new Object();
    private static SessionManager instance;

    /**
     * Returns instance of {@code SessionManager}. Creates manager if necessary.
     * @param context Passed context of place its called.
     * @return {@code SessionManager} instance
     */
    public static SessionManager getInstance(Context context) {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new SessionManager(context.getApplicationContext());
            }
            return instance;
        }
    }

    private SessionManager(Context applicationContext) {
        final String preferenceFilename = ImConfigFactory.create().getPreferencesFileName();
        preferences = applicationContext.getSharedPreferences(preferenceFilename, Context.MODE_PRIVATE);
        listeners = new ArrayList<>();
        session = restoreFromPreferences();
        Timber.d("Restored last session [%s]", session);
    }

    /**
     * Subscribes callback for objects which may need information about changes user session state.
     * If session is of this call authenticated with full user information is notify callback of user session state.
     * @param callback {@link OnSessionStateChanged} callback subscriber
     *
     * @see #removeOnStateChangedListener(OnSessionStateChanged)
     * @see #removeListeners()
     */
    public void addOnStateChangedListener(final OnSessionStateChanged callback) {
        if (!listeners.contains(callback)) {
            listeners.add(callback);
            if (session != null && session.isAuthenticated() && session.getUserProfile().getId() > 0) {
                notifyListeners(session);
            }
        }
    }

    /**
     * Removes previously added callback of user session state change.
     * @param callback
     *
     * @see #addOnStateChangedListener(OnSessionStateChanged)
     * @see #removeListeners()
     */
    public void removeOnStateChangedListener(final OnSessionStateChanged callback) {
        listeners.remove(callback);
    }

    /**
     * Removes all attached callbacks on {@code OnSessionStateChanged} action.
     *
     * @see this#addOnStateChangedListener(OnSessionStateChanged)
     * @see this#removeOnStateChangedListener(OnSessionStateChanged)
     */
    public void removeListeners() {
        listeners.clear();
    }

    /**
     * Start authentication with provided user credentials.
     * @param email user email
     * @param secret user password
     */
    public void authenticate(final String email, final String secret) {
        initializeRealmIfNecessary();
        fetchProfiles(email, secret);
        Timber.d("Authentication started [status=%s]", session);
    }

    private void initializeRealmIfNecessary() {
        if (realm == null || realm.isClosed()) {
            realm = Realm.getDefaultInstance();
        }
    }

    private void fetchProfiles(final String email, final String secret) {
        Timber.d("Authentication: fetching user profiles started [%s]", session);
        final Call<List<UserProfile>> userProfileCall = VolontuloApp.api.listVolunteers();
        userProfileCall.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                if (response.isSuccessful()) {
                    final List<UserProfile> profiles = response.body();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(profiles);
                    realm.commitTransaction();

                    Timber.d("Authentication: user profiles count %d fetched", profiles.size());
                    login(email, secret);

                }
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
            }
        });
    }

    private void login(final String email, final String secret) {
        final Call<LoginResponse> loginResponseCall = VolontuloApp.api.login(email, secret);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                session = Session.UNAUTHENTICATED;
                if (response.isSuccessful()) {
                    final LoginResponse loginResponse = response.body();
                    if (loginResponse != null && !TextUtils.isEmpty(loginResponse.getKey())) {
                        final UserProfile profile = findProfile(email);
                        if (profile == null) {
                            session = new Session.Builder(loginResponse.getKey(), Boolean.TRUE)
                                    .build();
                            Timber.i("Authentication success, but some info is incomplete [%s]", session);
                            storeInPreferences(session);
                            fetchProfiles(email, secret);
                        } else {
                            session = new Session.Builder(loginResponse.getKey(), Boolean.TRUE)
                                    .withProfile(profile)
                                    .build();
                            Timber.i("Authentication success complete [%s]", session);
                            storeInPreferences(session);
                        }
                    } else {
                        Timber.i("Authentication failed: bad response [%s]", loginResponse);
                    }
                } else {
                    Timber.i("Authentication failed %s [%d %s]", session, response.code(), bodyToString(response.errorBody()));
                }
                notifyListeners(session);
            }

            private String bodyToString(final ResponseBody responseBody) {
                String result = "";
                try {
                    result = responseBody.string();
                } catch (IOException e) {
                }
                return result;
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Timber.e(t, "Authentication failed due to connection problem");
                session = Session.UNAUTHENTICATED;
                notifyListeners(session);
            }
        });
    }

    private UserProfile findProfile(final String email) {
        final UserProfile profile = realm.where(UserProfile.class)
            .equalTo(String.format("%s.%s", UserProfile.FIELD_USER, User.FIELD_EMAIL), email)
            .findFirst();
        if (profile == null) {
            Timber.w("Not found user profile with email %s", email);
        } else {
            Timber.d("Found user profile %s", profile);
        }
        return profile;
    }

    private void notifyListeners(final Session session) {
        synchronized (listeners) {
            for (final OnSessionStateChanged listener : listeners) {
                if (listener != null) {
                    listener.onSessionStateChanged(session);
                }
            }
            Timber.d("All listeners notified about session state change");
        }
    }

    private void storeInPreferences(final Session session) {
        preferences.edit()
            .putString(PREF_SESSION_KEY, session.getToken())
            .putBoolean(PREF_SESSION_AUTH, session.isAuthenticated())
            .putInt(PREF_USER_PROFILE_ID, session.getUserProfile().getId())
            .apply();
    }

    private Session restoreFromPreferences() {
        initializeRealmIfNecessary();
        final String key = preferences.getString(PREF_SESSION_KEY, "");
        final boolean isAuthenticated = preferences.getBoolean(PREF_SESSION_AUTH, Boolean.FALSE);
        final UserProfile profile = realm.where(UserProfile.class).equalTo(UserProfile.FIELD_ID, preferences.getInt(PREF_USER_PROFILE_ID, -1)).findFirst();
        return new Session.Builder(key, isAuthenticated).withProfile(profile).build();
    }

    /**
     * Logout authenticated user and sets session to {@code Session.UNAUTHENTICATED}
     */
    public void deauthenticate() {
        preferences.edit().clear().apply();
        session = Session.UNAUTHENTICATED;
        notifyListeners(session);
    }

    /**
     * Returns current user session status.
     * @return {@code true} if user is authenticated, {@code false} otherwise.
     */
    public boolean isAuthenticated() {
        return session.isAuthenticated();
    }

    /**
     * Returns authenticated user profile.
     * @return {@link UserProfile}
     *
     * @see Session#getUserProfile()
     */
    public UserProfile getUserProfile() {
        return session.getUserProfile();
    }

    /**
     * Returns token used for authentication user actions.
     * @return session key formatted as token used for requests.
     *
     * @see #getSessionKey()
     */
    public String getSessionToken() {
        return "Token " + session.getToken();
    }

    /**
     * Returns session key used for authentication user actions.
     * @return session key string
     *
     * @see #getSessionToken()
     */
    public String getSessionKey() {
        return session.getToken();
    }
}
