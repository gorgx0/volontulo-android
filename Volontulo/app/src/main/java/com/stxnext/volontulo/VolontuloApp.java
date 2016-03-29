package com.stxnext.volontulo;

import android.app.Application;

import com.stxnext.volontulo.db.RealmConfigurator;

import net.danlew.android.joda.JodaTimeAndroid;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class VolontuloApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        final RealmConfiguration.Builder builder = new RealmConfiguration.Builder(this);
        Realm.setDefaultConfiguration(RealmConfigurator.prepare(builder));
    }
}
