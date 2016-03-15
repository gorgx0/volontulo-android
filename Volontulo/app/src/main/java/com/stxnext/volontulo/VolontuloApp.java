package com.stxnext.volontulo;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

public class VolontuloApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
