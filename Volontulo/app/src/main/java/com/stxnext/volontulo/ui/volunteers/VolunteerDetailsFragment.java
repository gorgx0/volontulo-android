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

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_volunteer_details;
    }

    private void obtainData(int userId) {
        final String msg = "UserID: " + userId;
        Log.d(TAG, msg);
        final Call<UserProfile> call = VolontuloApp.api.getVolunteer(userId);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                int statusCode = response.code();
                UserProfile userProfile = response.body();
                final String msg = "SUCCESS: status - " + statusCode;
                Log.d(TAG, msg);
                Log.d(TAG, userProfile.toString());
                final MockAttendsAdapter adapter = new MockAttendsAdapter(getContext());
                adapter.setUserProfile(userProfile);
                offers.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                final String msg = "FAILURE: message - " + t.getMessage();
                Log.d(TAG, msg);
            }
        });
    }


    protected void onPostCreateView(View root) {
        int userId = getArguments().getInt(User.USER_ID, 0);
        obtainData(userId);
        Context context = getContext();
        offers.setLayoutManager(new LinearLayoutManager(context));
        offers.setHasFixedSize(true);
    }
}
