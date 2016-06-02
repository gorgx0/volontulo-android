package com.stxnext.volontulo;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import timber.log.Timber;

public class VolontuloInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Timber.d("REAL BACKEND FLAVOUR INTERCEPTOR TEST!");
        return chain.proceed(chain.request());
    }
}
