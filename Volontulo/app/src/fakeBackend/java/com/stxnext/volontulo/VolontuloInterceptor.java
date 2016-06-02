package com.stxnext.volontulo;


import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class VolontuloInterceptor implements Interceptor {

    String LOGIN_RESPOSNE = "{\n" +
            "  \"key\": \"49af5f39b96e48e0575804dae37eb3abfa235e71\"\n" +
            "}";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Timber.d("FAKE BACKEND FLAVOUR INTERCEPTOR TEST!");

        // @TODO: Place for mocking requests!
        Response response = null;
        String responseString;
        final HttpUrl url = chain.request().url();
        final String query = url.uri().getQuery();
        responseString = LOGIN_RESPOSNE;
        response = new Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .addHeader("content-type", "application/json")
                .build();
        return response;
    }
}
