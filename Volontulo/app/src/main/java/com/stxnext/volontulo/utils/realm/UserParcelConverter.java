package com.stxnext.volontulo.utils.realm;

import android.os.Parcel;

import com.stxnext.volontulo.api.User;

import org.parceler.Parcels;

public class UserParcelConverter extends RealmListParcelConverter<User> {
    @Override
    public void itemToParcel(User input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public User itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(User.class.getClassLoader()));
    }
}
