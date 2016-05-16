package com.stxnext.volontulo.logic.session;

import com.stxnext.volontulo.api.UserProfile;

/**
 * State of current session in application.
 * This object is immutable.
 */
public final class Session {
    /**
     * Unauthenticated state which is returned if user not login or fail attempt to login. Default state of start session.
     */
    public static final Session UNAUTHENTICATED = new Builder("", Boolean.FALSE).build();

    private final UserProfile profile;
    private final String token;
    private final boolean isAuthenticated;

    /**
     * Builder for proper construct {@code Session} object.
     */
    public static class Builder {
        private UserProfile profile;
        private String token;
        private boolean isAuthenticated;

        /**
         * Construct builder object with required parameters.
         * @param token Hash token returned by server used for authentication actions that required user session.
         * @param isAuthenticated boolean value which indicate if user is authenticated or not.
         */
        public Builder(String token, boolean isAuthenticated) {
            this.profile = UserProfile.createEmpty();
            this.token = token;
            this.isAuthenticated = isAuthenticated;
        }

        /**
         * Binds {@code UserProfile} with {@code Session}.
         * @param profile
         * @return builder for chaining actions.
         */
        public Builder withProfile(UserProfile profile) {
            this.profile = UserProfile.copyObject(profile);
            return this;
        }

        /**
         * Constructs valid {@code Session} object.
         * @return session object based on provided parameters.
         */
        public Session build() {
            return new Session(profile, token, isAuthenticated);
        }
    }

    private Session(UserProfile profile, String key, boolean isAuthenticated) {
        this.profile = UserProfile.copyObject(profile);
        this.token = key;
        this.isAuthenticated = isAuthenticated;
    }

    /**
     * Returns assigned {@code UserProfile} to current session.
     * @return {@code UserProfile} binded to session or empty {@code UserProfile} if user information is unavailable.
     */
    public UserProfile getUserProfile() {
        return UserProfile.copyObject(profile);
    }

    /**
     * Returns token assigned to session by server.
     * @return valid token used for authentication actions.
     */
    public String getToken() {
        return token;
    }

    /**
     * Returns current status of user in session if is authenticated.
     * @return {@code true} if user is authenticated or {@code false} otherwise.
     */
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Session{" +
                "profile=" + profile +
                ", token='" + token + '\'' +
                ", isAuthenticated=" + isAuthenticated +
                '}';
    }
}
