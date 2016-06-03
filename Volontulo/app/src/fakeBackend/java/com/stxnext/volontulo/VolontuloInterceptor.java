package com.stxnext.volontulo;


import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.api.UserProfile;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

public class VolontuloInterceptor implements Interceptor {

    String LOGIN_RESPONSE_PASS = "{\n" +
            "  \"key\": \"49af5f39b96e48e0575804dae37eb3abfa235e71\"\n" +
            "}";

    String LOGIN_RESPONSE_FAIL = "{\n" +
            "  \"non_field_errors\": \"Podane dane uwierzytelniające nie pozwalają na zalogowanie.\"\n" +
            "}";

    String TEST_PASSWORD = "test123";

    private Context context;
    private Realm realm;

    public VolontuloInterceptor(Context ctx) {
        context = ctx;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Timber.d("FAKE BACKEND FLAVOUR INTERCEPTOR TEST!");
        final String encodedPath = chain.request().url().encodedPath();
        final Set<String> getRequests = new HashSet();
        getRequests.add("/api/offers.json");
        getRequests.add("/api/users_profiles.json");
        final Request request = chain.request();
        final FormBody body = (FormBody) request.body();

        Response response;
        String responseString = null;

        if (getRequests.contains(encodedPath)) {
            responseString = readAsset(encodedPath);
        } else if (encodedPath.equals("/rest-auth/login/")) {
            responseString = doLogin(body);
        } else if (encodedPath.equals("/api/offers/create/")) {
            responseString = doSave(body, 0);
        } else if (encodedPath.contains("/update/")) {
            final List<String> pathSegments = chain.request().url().encodedPathSegments();
            int id = Integer.parseInt(pathSegments.get(2));
            doSave(body, id);
        }

        response = new Response.Builder()
                .code(200)
                .message(responseString)
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .addHeader("content-type", "application/json")
                .build();
        return response;
    }

    private String doLogin(FormBody body) {
        if (body == null || body.size() != 2
                || body.encodedName(0).equals("username") || TextUtils.isEmpty(body.encodedValue(0))
                || body.encodedName(0).equals("password") || TextUtils.isEmpty(body.encodedValue(1))) {
            return LOGIN_RESPONSE_FAIL;
        } else {
            final String username = body.encodedValue(0);
            final String password = body.encodedValue(1);

            if (realm == null || realm.isClosed()) {
                realm = Realm.getDefaultInstance();
            }
            final UserProfile profile = realm.where(UserProfile.class).equalTo("user.email", username).findFirst();
            realm.close();

            if (TEST_PASSWORD.equals(password) && profile != null) {
                return LOGIN_RESPONSE_PASS;
            } else {
                return LOGIN_RESPONSE_FAIL;
            }
        }
    }

    private String doSave(FormBody body, int id) throws IOException {
        String response;
        if (body == null || body.size() != 7) {
            response = readAsset("/api/save_error.json");
            response = response.replace("%%detail%%", "Something goes wrong...");
        } else {
            response = readAsset("/api/save_pass.json");
            final int size = body.size();
            int i;
            for (i = 0; i < size; i++) {
                response = response.replace("%%" + body.encodedName(i) + "%%", body.encodedValue(i));
            }
            if (id != 0) {
                id = findNextId();
            }
            response = response.replace("%%id%%", String.valueOf(id));
        }
        return response;
    }

    private int findNextId() {
        if (realm == null || realm.isClosed()) {
            realm = Realm.getDefaultInstance();
        }
        final Number id = realm.where(Offer.class).max("id");
        realm.close();
        return  id.intValue() == 0 ? 1 : id.intValue();
    }

    @NonNull
    private String readAsset(String filePath) throws IOException {
        String response;
        InputStream is = context.getAssets().open(filePath.substring(1));
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        response = new String(buffer);
        return response;
    }
}
