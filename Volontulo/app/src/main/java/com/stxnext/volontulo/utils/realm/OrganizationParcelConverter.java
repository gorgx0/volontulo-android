package com.stxnext.volontulo.utils.realm;

import android.os.Parcel;

import com.stxnext.volontulo.api.Organization;

import org.parceler.Parcels;

public class OrganizationParcelConverter extends RealmListParcelConverter<Organization> {
    @Override
    public void itemToParcel(Organization input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Organization itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Organization.class.getClassLoader()));
    }
}
