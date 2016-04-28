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
            return "3bd1dfde-c988-4c25-b55b-00b7dd4990aa";
        }

        @Override
        public String getSecret() {
            return "A/yJIn7CA0ekNbManKiE4w==";
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
