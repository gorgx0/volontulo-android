package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.Image;
import com.stxnext.volontulo.api.Offer;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
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

    @OnClick(R.id.button_step_out)
    public void doStepOut(View view) {
        Snackbar.make(view, "Zgłosiłeś się!!!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Bundle arguments = getArguments();
        int id = arguments.getInt(Offer.OFFER_ID, 0);
        imageResource = arguments.getInt(Offer.IMAGE_RESOURCE, R.drawable.ice);
        if (arguments.containsKey(Offer.IMAGE_PATH)) {
            imagePath = arguments.getString(Offer.IMAGE_PATH);
        }
        obtainData(id);
    }

    private void obtainData(int id) {
        final Call<Offer> call = VolontuloApp.api.getOffer(id);
        call.enqueue(new Callback<Offer>() {
            @Override
            public void onResponse(Call<Offer> call, Response<Offer> response) {
                int statusCode = response.code();
                final com.stxnext.volontulo.api.Offer offer = response.body();
                final String msg = "SUCCESS: status - " + statusCode;
                Log.d(TAG, msg);
//                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                Log.d(TAG, offer.toString());
                title.setText(offer.getTitle());
                location.setText(offer.getLocation());
                duration.setText(offer.getDuration(getString(R.string.now), getString(R.string.to_set)));
                description.setText(offer.getDescription());
                requirements.setText(offer.getRequirements());
                benefits.setText(offer.getBenefits());
                timeCommitment.setText(offer.getTimeCommitment());
                organization.setText(offer.getOrganization().getName());
                final List<Image> images = offer.getImages();
                if (offer.hasImage()) {
                    imagePath = offer.getImagePath();
                    imageResource = 0;
                }
            }

            @Override
            public void onFailure(Call<com.stxnext.volontulo.api.Offer> call, Throwable t) {
                final String msg = "FAILURE: message - " + t.getMessage();
                Log.d(TAG, msg);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.offer_details_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_join_offer:
                doStepOut(getView());
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
