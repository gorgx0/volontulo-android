package com.stxnext.volontulo.db;

import io.realm.RealmConfiguration;

public final class RealmConfigurator {
    public static RealmConfiguration prepare(final RealmConfiguration.Builder builder) {
        return builder.build();
    }
}
