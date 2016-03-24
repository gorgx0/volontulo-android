package com.stxnext.volontulo.model;

import android.support.annotation.DrawableRes;

import org.parceler.Parcel;

import io.realm.RealmObject;
import io.realm.VolunteerRealmProxy;
import io.realm.annotations.Ignore;

@Parcel(implementations = { VolunteerRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Volunteer.class })
public class Volunteer extends RealmObject {
    private String name;
    private String surname;

    @Ignore private int avatarResouce;

    public static Volunteer mock(String name, String surname, @DrawableRes int imageRes) {
        final Volunteer result = new Volunteer();
        result.name = name;
        result.surname = surname;
        result.avatarResouce = imageRes;
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAvatarResouce() {
        return avatarResouce;
    }

    public void setAvatarResouce(int avatarResouce) {
        this.avatarResouce = avatarResouce;
    }
}
