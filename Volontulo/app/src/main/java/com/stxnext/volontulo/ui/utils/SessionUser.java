package com.stxnext.volontulo.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.api.LoginResponse;
import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.logic.im.config.ImConfigFactory;

import java.io.IOException;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SessionUser {

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

    public  SessionUser(Context ctx) {
        context = ctx;
        final String preferencesFileName = ImConfigFactory.create().getPreferencesFileName();
        preferences = context.getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE);
        restoreData();
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
        return isLogged;
    }

    public void login(String email, String password) {
        Call<LoginResponse> call = VolontuloApp.api.login(email, password);
        try {
            Response<LoginResponse> response = call.execute();
            if (response.isSuccessful()) {
                LoginResponse loginResponse = response.body();
                if (loginResponse != null) {
                    if (!TextUtils.isEmpty(loginResponse.getKey())) {
                        setKey(loginResponse.getKey());
                        setIsLogged(true);
                        findUser(email);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findUser(final String email) {
        realm = Realm.getDefaultInstance();
        UserProfile profile = realm.where(UserProfile.class).equalTo("user.email", email).findFirst();
        if (profile != null) {
            storeData(profile);
        } else {
            Call<List<UserProfile>> call = VolontuloApp.api.listVolunteers();
            call.enqueue(new Callback<List<UserProfile>>() {
                @Override
                public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                    if (response.isSuccessful()) {
                        List<UserProfile> userProfiles = response.body();
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(userProfiles);
                        realm.commitTransaction();
                        UserProfile profile = realm.where(UserProfile.class).equalTo("user.email", email).findFirst();
                        if (profile != null) {
                            storeData(profile);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                }
            });
        }
    }

    private void storeData(UserProfile profile) {
        userProfile = profile;
        user = userProfile.getUser();
        setUserId(user.getId());
        setEmail(user.getEmail());
        setFullname(user.getUsername());
        setPhoneNo(userProfile.getPhoneNo());
        if (!realm.isClosed()) {
            realm.close();
        }
    }

    private void restoreData() {
        isLogged = preferences.getBoolean(PREF_USER_IS_LOGGED, false);
        userId = preferences.getInt(PREF_USER_ID, 0);
        userProfileId = preferences.getInt(PREF_USER_PROFILE_ID, 0);
        email = preferences.getString(PREF_USER_EMAIL, null);
        fullname = preferences.getString(PREF_USER_FULLNAME, null);
        phoneNo = preferences.getString(PREF_USER_PHONE_NO, null);
        realm = Realm.getDefaultInstance();
        UserProfile profile = realm.where(UserProfile.class).equalTo("id", userProfileId).findFirst();
        if (profile != null) {
            userProfile = profile;
            user = userProfile.getUser();
            realm.close();
        } else {
            Call<UserProfile> call = VolontuloApp.api.getVolunteer(userProfileId);
            call.enqueue(new Callback<UserProfile>() {
                @Override
                public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                    if (response.isSuccessful()) {
                        UserProfile profile = response.body();
                        if (profile != null) {
                            userProfile = profile;
                            user = userProfile.getUser();
                            realm.beginTransaction();
                            realm.copyToRealmOrUpdate(profile);
                            realm.commitTransaction();
                            realm.close();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserProfile> call, Throwable t) {
                }
            });
        }
    }
}
