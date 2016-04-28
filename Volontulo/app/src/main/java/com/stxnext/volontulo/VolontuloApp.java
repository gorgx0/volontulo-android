package com.stxnext.volontulo;

import android.app.Application;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stxnext.volontulo.db.RealmConfigurator;

import net.danlew.android.joda.JodaTimeAndroid;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VolontuloApp extends Application {

    public static final String API_ENDPOINT = "http://volontuloapp.stxnext.local";
    public static VolontuloApi api;
    public static VolontuloApi cachedApi;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        final RealmConfiguration.Builder realmBuilder = new RealmConfiguration.Builder(this);
        Realm.setDefaultConfiguration(RealmConfigurator.prepare(realmBuilder));

        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create();

        final Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = retrofitBuilder.build();
        api = retrofit.create(VolontuloApi.class);

        final Cache cache = new Cache(new File(getCacheDir(), String.valueOf(UUID.randomUUID())), 1024 * 1024 * 10);
        final OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        final Request request = chain.request()
                                .newBuilder()
                                .build();
                        final Response response = chain.proceed(request);
                        return response.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", "public,max-age=120")
                                .build();
                    }
                })
                .build();
        Retrofit cachedRetrofit = retrofitBuilder
                .client(httpClient)
                .build();
        cachedApi = cachedRetrofit.create(VolontuloApi.class);
    }
}
