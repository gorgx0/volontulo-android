package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.UserProfile;

import org.parceler.Parcels;

import butterknife.Bind;
import io.realm.Realm;

public class VolunteerDetailsFragment extends VolontuloBaseFragment {

    public static final String TAG = "RETROFIT-TEST";

    private UserProfile userProfile;

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


    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
    }

    protected void onPostCreateView(View root) {
        final Bundle arguments = getArguments();
        userProfile = Parcels.unwrap(arguments.getParcelable(UserProfile.USER_PROFILE_OBJECT));
        Context context = getContext();
        offers.setLayoutManager(new LinearLayoutManager(context));
        offers.setHasFixedSize(true);

        final MockAttendsAdapter adapter = new MockAttendsAdapter(getContext());
        adapter.setUserProfile(userProfile);
        offers.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }
}
