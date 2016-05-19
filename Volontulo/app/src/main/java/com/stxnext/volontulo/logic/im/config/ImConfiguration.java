package com.stxnext.volontulo.logic.im.config;

import android.support.annotation.NonNull;

/**
 * Configuration used by instant messaging service for settings itself.
 */
public interface ImConfiguration {
    /**
     * Returns api key used for access to an instant messaging service.
     * @return string with valid api key. Cannot be null.
     */
    @NonNull
    String getApiKey();

    /**
     * Returns secret used for identify application in an instant messaging service.
     * @return string with valid secret. Cannot be null.
     */
    @NonNull
    String getSecret();

    /**
     * Returns host which is used for sending and receiving from messages.
     * @return string which is valid hostname or ip address. Cannot be null.
     */
    @NonNull
    String getEnvironmentHost();

    /**
     * Returns shared preferences file name. It is used for session management to cache user details.
     * @return non-empty string with filename.
     */
    @NonNull
    String getPreferencesFileName();
}
