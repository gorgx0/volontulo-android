package com.stxnext.volontulo;

import android.app.Application;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stxnext.volontulo.db.RealmConfigurator;

import net.danlew.android.joda.JodaTimeAndroid;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VolontuloApp extends Application {

    public static final String API_ENDPOINT = "http://volontuloapp.stxnext.local";
    public static VolontuloApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        final RealmConfiguration.Builder builder = new RealmConfiguration.Builder(this);
        Realm.setDefaultConfiguration(RealmConfigurator.prepare(builder));

        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaredClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(VolontuloApi.class);
    }
}
