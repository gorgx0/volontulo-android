package com.stxnext.volontulo.logic.im.config;

import android.support.annotation.NonNull;

public interface ImConfiguration {
    @NonNull
    String getApiKey();

    @NonNull
    String getSecret();

    @NonNull
    String getEnvironmentHost();

    @NonNull
    String getPreferencesFileName();
}
