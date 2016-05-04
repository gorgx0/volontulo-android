package com.stxnext.volontulo.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.api.LoginResponse;
import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.logic.im.config.ImConfigFactory;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionUser {


    private static final String TAG = "VOLONTULO-SESSION-USER";
    private static final String PREF_USER_PROFILE_ID = "PREF-USER-PROFILE-ID";
    private static final String PREF_USER_ID = "PREF-USER-ID";
    private static final String PREF_USER_IS_LOGGED = "PREF-USER-IS-LOGGED";
    private static final String PREF_USER_KEY = "PREF-USER-KEY";
    private static final String PREF_USER_EMAIL = "PREF-USER-EMAIL";
    private static final String PREF_USER_PHONE_NO = "PREF-USER-PHONE-NO";
    private static final String PREF_USER_FULLNAME = "PREF-USER-FULLNAME";

    private User user;
    private UserProfile userProfile;
    private Context context;
    private SharedPreferences preferences;

    private boolean isLogged;
    private String key, email, phoneNo, fullname;
    private int userId, userProfileId;
    private Realm realm;
    private LoginFinish loginFinish;

    public SessionUser(Context ctx) {
        context = ctx;
        final String preferencesFileName = ImConfigFactory.create().getPreferencesFileName();
        preferences = context.getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE);
        restoreData();
    }

    public interface LoginFinish {
        void onLoginFinished();
    }

    public void registerLoginFinish(LoginFinish lf) {
        loginFinish = lf;
    }

    public void unregisterLoginFinish() {
        loginFinish = null;
    }

    public void setIsLogged(boolean logged) {
        isLogged = logged;
        preferences.edit().putBoolean(PREF_USER_IS_LOGGED, logged).apply();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        preferences.edit().putString(PREF_USER_KEY, key).apply();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        preferences.edit().putString(PREF_USER_EMAIL, email).apply();
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        preferences.edit().putString(PREF_USER_PHONE_NO, phoneNo).apply();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
        preferences.edit().putString(PREF_USER_FULLNAME, fullname).apply();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
        preferences.edit().putInt(PREF_USER_ID, userId).apply();
    }

    public int getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(int userProfileId) {
        this.userProfileId = userProfileId;
        preferences.edit().putInt(PREF_USER_PROFILE_ID, userProfileId).apply();
    }

    public boolean isLogged() {
        Log.d(TAG, "IS-LOGGED: " + (isLogged ? "true" : "false"));
        return isLogged;
    }

    public void logout() {
        Log.d(TAG, "LOGOUT");
        setKey(null);
        setIsLogged(false);
        userProfile = null;
        user = null;
        setUserId(0);
        setUserProfileId(0);
        setEmail(null);
        setFullname(null);
        setPhoneNo(null);
    }

    public void login(final String email, final String password) {
        Call<LoginResponse> call = VolontuloApp.api.login(email, password);
        Log.d(TAG, String.format("LOGIN [RETRO] - email: '%s', passwd: '%s'", email, password));
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d(TAG, "LOGIN [RETRO] ENQUEUED");
                if (response.isSuccessful()) {
                    Log.d(TAG, "LOGIN [RETRO] SUCCESSFUL");
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        if (!TextUtils.isEmpty(loginResponse.getKey())) {
                            Log.d(TAG, "USER-LOGGED [RETRO]");
                            setKey(loginResponse.getKey());
                            setIsLogged(true);
                            findUser(email);
                            if (loginFinish != null) {
                                loginFinish.onLoginFinished();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                if (loginFinish != null) {
                    loginFinish.onLoginFinished();
                }
            }
        });
    }

    private void findUser(final String email) {
        initRealm();
        UserProfile profile = realm.where(UserProfile.class).equalTo("user.email", email).findFirst();
        Log.d(TAG, "FIND-USER");
        if (profile != null) {
            storeData(profile);
        } else {
            Call<List<UserProfile>> call = VolontuloApp.api.listVolunteers();
            Log.d(TAG, "FIND-USER [RETRO]");
            call.enqueue(new Callback<List<UserProfile>>() {
                @Override
                public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                    Log.d(TAG, "FIND-USER [RETRO] ENQUEUED");
                    if (response.isSuccessful()) {
                        initRealm();
                        List<UserProfile> userProfiles = response.body();
                        Log.d(TAG, "FIND-USER [RETRO] SUCCESS; userProfiles: " + userProfiles.size());
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(userProfiles);
                        realm.commitTransaction();
                        UserProfile profile = realm.where(UserProfile.class).equalTo("user.email", email).findFirst();
                        if (profile != null) {
                            storeData(profile);
                        }
                        realm.close();
                    }
                }

                @Override
                public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                    Log.d(TAG, "FIND-USER [RETRO] FAILURE");
                }
            });
        }
        realm.close();
    }

    private void storeData(UserProfile profile) {
        userProfile = profile;
        user = userProfile.getUser();
        setUserId(user.getId());
        setUserProfileId(profile.getId());
        setEmail(user.getEmail());
        setFullname(user.getUsername());
        setPhoneNo(userProfile.getPhoneNo());
        Log.d(TAG, "STORE-DATA");
    }

    private void restoreData() {
        Log.d(TAG, "RESTORE-DATA");
        isLogged = preferences.getBoolean(PREF_USER_IS_LOGGED, false);
        key = preferences.getString(PREF_USER_KEY, null);
        userId = preferences.getInt(PREF_USER_ID, 0);
        userProfileId = preferences.getInt(PREF_USER_PROFILE_ID, 0);
        email = preferences.getString(PREF_USER_EMAIL, null);
        fullname = preferences.getString(PREF_USER_FULLNAME, null);
        phoneNo = preferences.getString(PREF_USER_PHONE_NO, null);
        initRealm();
        UserProfile profile = realm.where(UserProfile.class).equalTo("id", userProfileId).findFirst();
        if (profile != null) {
            userProfile = profile;
            user = userProfile.getUser();
            Log.d(TAG, "RESTORE-DATA from REALM; profile: " + profile.toString());
            realm.close();
        } else {
            Call<UserProfile> call = VolontuloApp.api.getVolunteer(userProfileId);
            call.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                    if (response.isSuccessful()) {
                        UserProfile profile = response.body();
                        if (profile != null) {
                            initRealm();
                            userProfile = profile;
                            user = userProfile.getUser();
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(profile);
                            realm.commitTransaction();
                            realm.close();
                            Log.d(TAG, "RESTORE-DATA from RETRO; profile: " + profile.toString());
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserProfile> call, Throwable t) {
                    Log.d(TAG, "RESTORE-DATA [RETRO] FAILURE");
                }
            });
        }
        realm.close();
    }

    private void initRealm() {
        if (realm == null || realm.isClosed()) {
            realm = Realm.getDefaultInstance();
        }
    }
}
