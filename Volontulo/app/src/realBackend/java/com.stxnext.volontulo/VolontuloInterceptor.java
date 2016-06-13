package com.stxnext.volontulo;


import android.content.Context;
import android.text.TextUtils;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

public class VolontuloInterceptor implements Interceptor {

    private Context context;

    public VolontuloInterceptor(Context ctx) {
        context = ctx;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Timber.i("INTERCEPT[encodedPath]: %s", chain.request().url().encodedPath());
        final FormBody body = (FormBody) chain.request().body();

        if (body != null
                && body.size() != 0) {
            int i;
            int size = body.size();
            String data[] = new String[size];
            for (i = 0; i < size; i++) {
                final String msg = String.format("%s: '%s'", body.encodedName(i), body.encodedValue(i));
                data[i] = msg;
            }
            Timber.i("INTERCEPT[params-%d]: %s", size, TextUtils.join("; ", data));
        }
        return chain.proceed(chain.request());
    }
}
