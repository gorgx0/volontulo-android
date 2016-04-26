package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.api.UserProfile;

import butterknife.Bind;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerDetailsFragment extends VolontuloBaseFragment {

    public static final String TAG = "RETROFIT-TEST";

    @Bind(R.id.text_name)
    TextView name;

    @Bind(R.id.text_email)
    TextView email;

    @Bind(R.id.text_description)
    TextView description;

    @Bind(R.id.text_phone)
    TextView phone;

    @Bind(R.id.image)
    ImageView image;

    @Bind(R.id.offers)
    protected RecyclerView offers;
    private Realm realm;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_volunteer_details;
    }

    private void obtainData(final int userId) {
        final String msg = "UserID: " + userId;
        Log.d(TAG, msg);
        final Call<UserProfile> call = VolontuloApp.api.getVolunteer(userId);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                UserProfile userProfile;
                String msg;
                if (response.isSuccessful()) {
                    userProfile = response.body();
                    msg = "[RETRO] " + userProfile.toString();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(userProfile);
                    realm.commitTransaction();
                } else {
                    userProfile = realm.where(UserProfile.class).equalTo("id", userId).findFirst();
                    msg = "[REALM] " + userProfile.toString();
                }

                Log.d(TAG, msg);
                if (userProfile != null) {
//                    final MockAttendsAdapter adapter = new MockAttendsAdapter(getContext());
//                    adapter.setUserProfile(userProfile);
//                    offers.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                String msg = "FAILURE: message - " + t.getMessage();
                Log.d(TAG, msg);
                UserProfile userProfile = realm.where(UserProfile.class).equalTo("id", userId).findFirst();
                msg = "[FAILURE] " + userProfile.toString();
                Log.d(TAG, msg);
//                final MockAttendsAdapter adapter = new MockAttendsAdapter(getContext());
//                adapter.setUserProfile(userProfile);
//                offers.setAdapter(adapter);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
    }

    protected void onPostCreateView(View root) {
        int userId = getArguments().getInt(User.USER_ID, 0);
        obtainData(userId);
        Context context = getContext();
        offers.setLayoutManager(new LinearLayoutManager(context));
        offers.setHasFixedSize(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }
}
