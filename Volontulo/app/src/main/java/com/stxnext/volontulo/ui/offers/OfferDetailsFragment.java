package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.Offer;

import butterknife.Bind;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferDetailsFragment extends VolontuloBaseFragment {

    public static final String TAG = "RETROFIT-TEST";

    @Bind(R.id.text_title)
    TextView title;

    @Bind(R.id.text_location)
    TextView location;

    @Bind(R.id.text_duration)
    TextView duration;

    @Bind(R.id.text_description)
    TextView description;

    @Bind(R.id.text_benefits)
    TextView benefits;

    @Bind(R.id.text_requirements)
    TextView requirements;

    @Bind(R.id.text_time_commitment)
    TextView timeCommitment;

    @Bind(R.id.text_organization)
    TextView organization;

    @DrawableRes
    private int imageResource;

    private String imagePath;
    private MenuItem itemJoined;
    private Realm realm;
    private int id;
    private Offer offer;

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_offers_details;
    }

    @Override
    public int getImageResource() {
        return imageResource;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Bundle arguments = getArguments();
        id = arguments.getInt(Offer.OFFER_ID, 0);
        imageResource = arguments.getInt(Offer.IMAGE_RESOURCE, R.drawable.ice);
        if (arguments.containsKey(Offer.IMAGE_PATH)) {
            imagePath = arguments.getString(Offer.IMAGE_PATH);
        }
        obtainData(id);
    }

    private void obtainData(final int id) {
        final Call<Offer> call = VolontuloApp.api.getOffer(id);
        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, Response<Offer> response) {
                String msg;
                if (response.isSuccessful()) {
                    offer = response.body();
                    msg = "[RETRO] " + offer.toString();
                    Log.d(TAG, msg);
                    Log.d(TAG, offer.toString());
                } else {
                    offer = realm.where(Offer.class).equalTo("id", id).findFirst();
                    msg = "[REALM] " + offer.toString();
                }

                Log.d(TAG, msg);
                fillData(offer);
            }

            @Override
            public void onFailure(Call<com.stxnext.volontulo.api.Offer> call, Throwable t) {
                String msg = "FAILURE: message - " + t.getMessage();
                Log.d(TAG, msg);
                offer = realm.where(Offer.class).equalTo("id", id).findFirst();
                fillData(offer);
                msg = "[FAILURE] " + offer.toString();
                Log.d(TAG, msg);
            }
        });
    }

    private void fillData(Offer offer) {
        title.setText(offer.getTitle());
        setToolbarTitle(offer.getTitle());
        location.setText(offer.getLocation());
        duration.setText(offer.getDuration(getString(R.string.now), getString(R.string.to_set)));
        description.setText(offer.getDescription());
        requirements.setText(offer.getRequirements());
        benefits.setText(offer.getBenefits());
        timeCommitment.setText(offer.getTimeCommitment());
        organization.setText(offer.getOrganization().getName());
        if (offer.hasImage()) {
            imagePath = offer.getImagePath();
            imageResource = 0;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        requestFloatingActionButton();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.offer_details_menu, menu);
        itemJoined = menu.findItem(R.id.action_offer_joined);
    }


    @Override
    protected void onFabClick(FloatingActionButton button) {
        offer.isUserJoined();
        button.setVisibility(View.GONE);
        itemJoined.setVisible(true);
        View view = getView();
        if (view != null) {
            Snackbar.make(view, "Zgłosiłeś się!!!", Snackbar.LENGTH_SHORT).show();
        }
    }
}
