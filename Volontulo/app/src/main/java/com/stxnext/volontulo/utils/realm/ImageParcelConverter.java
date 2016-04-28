package com.stxnext.volontulo.utils.realm;

import android.os.Parcel;

import com.stxnext.volontulo.api.Image;

import org.parceler.Parcels;

public class ImageParcelConverter extends RealmListParcelConverter<Image> {
    @Override
    public void itemToParcel(Image input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public Image itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(Image.class.getClassLoader()));
    }
}
