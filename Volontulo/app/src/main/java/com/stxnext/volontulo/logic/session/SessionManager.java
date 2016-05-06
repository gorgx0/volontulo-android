package com.stxnext.volontulo.logic.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

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

public class SessionManager {
    public interface OnSessionStateChanged {
        void onSessionStateChanged(Session session);
    }

    private SharedPreferences preferences;
    private volatile Session session;
    private Realm realm;
    private final List<OnSessionStateChanged> listeners;

    private static final String TAG = "Volontulo-SessionMgr";

    private static final String PREF_USER_PROFILE_ID = "PREF-USER-PROFILE-ID";
    private static final String PREF_SESSION_AUTH = "PREF-USER-IS-LOGGED";
    private static final String PREF_SESSION_KEY = "PREF-USER-KEY";

    private static final Object LOCK = new Object();
    private static SessionManager instance;
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
        Log.d(TAG, String.format("Restored last session [%s]", session));
    }

    public void addOnStateChangedListener(final OnSessionStateChanged callback) {
        if (!listeners.contains(callback)) {
            listeners.add(callback);
        }
    }

    public void removeOnStateChangedListener(final OnSessionStateChanged callback) {
        listeners.remove(callback);
    }

    public void removeListeners() {
        listeners.clear();
    }

    public void authenticate(final String email, final String secret) {
        initializeRealmIfNecessary();
        Log.d(TAG, String.format("Authentication started [status=%s]", session));
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
                            Log.i(TAG, String.format("Authentication success, but some info is incomplete [%s]", session));
                            storeInPreferences(session);
                            fetchProfiles(email);
                        } else {
                            session = new Session.Builder(loginResponse.getKey(), Boolean.TRUE)
                                .withProfile(profile)
                                .build();
                            Log.i(TAG, String.format("Authentication success complete [%s]", session));
                            storeInPreferences(session);
                        }
                    } else {
                        Log.w(TAG, String.format("Authentication failed: bad response [%s]", loginResponse));
                    }
                } else {
                    Log.w(TAG, String.format("Authentication failed %s [%d %s]", session, response.code(), bodyToString(response.errorBody())));
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
                Log.e(TAG, "Authentication failed due to connection problem", t);
                session = Session.UNAUTHENTICATED;
                notifyListeners(session);
            }
        });
    }

    private void initializeRealmIfNecessary() {
        if (realm == null || realm.isClosed()) {
            realm = Realm.getDefaultInstance();
        }
    }

    private void fetchProfiles(final String email) {
        Log.d(TAG, String.format("Authentication: fetching user profiles started [%s]", session));
        final Call<List<UserProfile>> userProfileCall = VolontuloApp.api.listVolunteers();
        userProfileCall.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                if (response.isSuccessful()) {
                    final List<UserProfile> profiles = response.body();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(profiles);
                    realm.commitTransaction();

                    Log.d(TAG, String.format("Authentication: user profiles count %d fetched", profiles.size()));

                    final UserProfile profile = findProfile(email);
                    if (profile != null) {
                        final String key = session.getToken();
                        final boolean status = session.isAuthenticated();
                        session = new Session.Builder(key, status).withProfile(profile).build();
                        notifyListeners(session);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
            }
        });
    }

    private UserProfile findProfile(final String email) {
        final UserProfile profile = realm.where(UserProfile.class)
            .equalTo(String.format("%s.%s", UserProfile.FIELD_USER, User.FIELD_EMAIL), email)
            .findFirst();
        if (profile == null) {
            Log.w(TAG, String.format("Not found user profile with email %s", email));
        } else {
            Log.d(TAG, String.format("Found user profile %s", profile));
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
        final UserProfile profile = realm.where(UserProfile.class).equalTo("id", preferences.getInt(PREF_USER_PROFILE_ID, -1)).findFirst();
        return new Session.Builder(key, isAuthenticated).withProfile(profile).build();
    }

    public void deauthenticate() {
        preferences.edit().clear().apply();
        session = Session.UNAUTHENTICATED;
        notifyListeners(session);
    }

    public boolean isAuthenticated() {
        return session.isAuthenticated();
    }

    public UserProfile getUserProfile() {
        return session.getUserProfile();
    }

    public String getSessionToken() {
        return session.getToken();
    }
}
