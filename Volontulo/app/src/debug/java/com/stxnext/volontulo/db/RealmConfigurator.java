package com.stxnext.volontulo.db;

import io.realm.RealmConfiguration;

public final class RealmConfigurator {
    public static RealmConfiguration prepare(RealmConfiguration.Builder builder) {
        return builder
            .deleteRealmIfMigrationNeeded()
            .build();
    }
}
