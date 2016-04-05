package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.VolontuloBaseFragment;
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

    @Bind(R.id.image)
    ImageView image;

    @Bind(R.id.offers)
    protected RecyclerView offers;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_volunteer_details;
    }

    private void obtainData() {
        final Call<UserProfile> call = VolontuloApp.api.getVolunteer(57);
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                int statusCode = response.code();
                UserProfile userProfile = response.body();
                final String msg = "SUCCESS: status - " + statusCode;
                Log.d(TAG, msg);
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                Log.d(TAG, userProfile.toString());
                name.setText(userProfile.resolveName());
                email.setText(userProfile.getEmail());
                Picasso.with(image.getContext())
                        .load(userProfile.getImage())
                        .fit()
                        .centerCrop()
                        .placeholder(R.drawable.ic_user_placeholder)
                        .into(image);
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                final String msg = "FAILURE: message - " + t.getMessage();
                Log.d(TAG, msg);
            }
        });
    }


    protected void onPostCreateView(View root) {
        obtainData();
        Context context = getContext();
        offers.setLayoutManager(new LinearLayoutManager(context));
        offers.setHasFixedSize(true);
        offers.setAdapter(new MockAttendsAdapter(context));
    }
}
