package com.stxnext.volontulo.utils.realm;

import io.realm.RealmObject;

public class RealmString extends RealmObject {
    private String value;

    public RealmString() {
        value = "";
    }

    public RealmString(String newValue) {
        value = newValue;
    }

    public RealmString(RealmString string) {
        this(string.get());
    }

    public String get() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
