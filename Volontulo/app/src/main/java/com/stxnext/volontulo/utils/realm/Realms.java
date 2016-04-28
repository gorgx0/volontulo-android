package com.stxnext.volontulo.utils.realm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.RealmList;

public final class Realms {
    public static List<String> realmAsNormal(RealmList<RealmString> dbList) {
        if (dbList == null) {
            return Collections.emptyList();
        }
        final List<String> result = new ArrayList<>(dbList.size());
        for (RealmString realmString : dbList) {
            result.add(realmString.getValue());
        }
        return result;
    }

    public static RealmList<RealmString> normalAsRealm(List<String> list) {
        final RealmList<RealmString> result = new RealmList<>();
        for (String string : list) {
            result.add(new RealmString(string));
        }
        return result;
    }

    private Realms() {
    }
}
