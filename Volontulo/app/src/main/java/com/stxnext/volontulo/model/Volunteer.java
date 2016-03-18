package com.stxnext.volontulo.model;

import android.support.annotation.DrawableRes;

public class Volunteer {
    private String name;
    private String surname;
    private int avatarResouce;

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
