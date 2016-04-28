package com.stxnext.volontulo.utils.realm;

import org.parceler.converter.CollectionParcelConverter;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Abstract converter for parcelling {@code RealmList} objects with Parceler library.
 * @param <T> Type of objects that {@code RealmList} holds.
 */
public abstract class RealmListParcelConverter<T extends RealmObject> extends CollectionParcelConverter<T, RealmList<T>> {
    @Override
    public RealmList<T> createCollection() {
        return new RealmList<>();
    }
}
