package com.stxnext.volontulo.logic.im.config;

import com.stxnext.volontulo.BuildConfig;

public final class ImConfigFactory {
    public static ImConfiguration create() {
        if (BuildConfig.DEBUG) {
            return new SinchDebugConfiguration();
        } else {
            return new SinchReleaseConfiguration();
        }
    }

    private static final class SinchDebugConfiguration implements ImConfiguration {
        @Override
        public String getApiKey() {
            return "dbadd3f7-2ce7-40e9-806b-7346c4f295f2";
        }

        @Override
        public String getSecret() {
            return "lmJuLH51RUmKzK4z+SQSTA==";
        }

        @Override
        public String getEnvironmentHost() {
            return "sandbox.sinch.com";
        }

        @Override
        public String getPreferencesFileName() {
            return "debug-user";
        }
    }

    private static final class SinchReleaseConfiguration implements ImConfiguration {
        @Override
        public String getApiKey() {
            return EMPTY;
        }

        @Override
        public String getSecret() {
            return EMPTY;
        }

        @Override
        public String getEnvironmentHost() {
            return EMPTY;
        }

        @Override
        public String getPreferencesFileName() {
            return EMPTY;
        }
    }

    private static final String EMPTY = "";

    private ImConfigFactory() {
        throw new IllegalAccessError("Cannot instantiate Factory class, use static methods");
    }
}