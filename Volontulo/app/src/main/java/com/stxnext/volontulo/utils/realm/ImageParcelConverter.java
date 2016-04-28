package com.stxnext.volontulo.utils.realm;

import android.os.Parcel;

import org.parceler.Parcels;

public class ImageParcelConverter extends RealmListParcelConverter<RealmString> {
    @Override
    public void itemToParcel(RealmString input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public RealmString itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(RealmString.class.getClassLoader()));
    }
}
