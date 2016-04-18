package com.stxnext.volontulo.utils.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmTuple extends RealmObject {
    @PrimaryKey
    private String key;
    private String value;

    public RealmTuple() {
    }

    public RealmTuple(RealmTuple tuple) {
        this(tuple.getKey(), tuple.getValue());
    }

    public RealmTuple(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
