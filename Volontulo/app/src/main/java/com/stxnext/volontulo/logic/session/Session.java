package com.stxnext.volontulo.logic.session;

import com.stxnext.volontulo.api.UserProfile;

/**
 * State of current session in application.
 * This object is immutable.
 */
public final class Session {
    public static final Session UNAUTHENTICATED = new Builder("", Boolean.FALSE).build();

    private final UserProfile profile;
    private final String token;
    private final boolean isAuthenticated;

    public static class Builder {
        private UserProfile profile;
        private String token;
        private boolean isAuthenticated;

        public Builder(String token, boolean isAuthenticated) {
            this.token = token;
            this.isAuthenticated = isAuthenticated;
        }

        public Builder withProfile(UserProfile profile) {
            this.profile = UserProfile.copyObject(profile);
            return this;
        }

        public Session build() {
            return new Session(profile, token, isAuthenticated);
        }
    }

    private Session(UserProfile profile, String key, boolean isAuthenticated) {
        this.profile = profile;
        this.token = key;
        this.isAuthenticated = isAuthenticated;
    }

    public UserProfile getUserProfile() {
        return UserProfile.copyObject(profile);
    }

    public String getToken() {
        return token;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public String toString() {
        return "Session{" +
                "profile=" + profile +
                ", token='" + token + '\'' +
                ", isAuthenticated=" + isAuthenticated +
                '}';
    }
}
