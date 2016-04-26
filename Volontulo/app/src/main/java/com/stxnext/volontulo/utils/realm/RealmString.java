package com.stxnext.volontulo.utils.realm;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.RealmStringRealmProxy;

@Parcel(implementations = {RealmStringRealmProxy.class},
    value = Parcel.Serialization.BEAN,
    analyze = {RealmString.class})
public class RealmString extends RealmObject {
    public static final String FIELD_VALUE = "value";

    private String value;

    public RealmString() {
        value = "";
    }

    public RealmString(RealmString string) {
        this(string.value);
    }

    public RealmString(String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
