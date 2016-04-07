package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.Image;
import com.stxnext.volontulo.model.Offer;

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
        imageResource = getArguments().getInt(Offer.IMAGE_RESOURCE, R.drawable.ice);
        obtainData();
    }

    private void obtainData() {
        final Call<com.stxnext.volontulo.api.Offer> call = VolontuloApp.api.getOffer(8);
        call.enqueue(new Callback<com.stxnext.volontulo.api.Offer>() {
            @Override
            public void onResponse(Call<com.stxnext.volontulo.api.Offer> call, Response<com.stxnext.volontulo.api.Offer> response) {
                int statusCode = response.code();
                final com.stxnext.volontulo.api.Offer offer = response.body();
                final String msg = "SUCCESS: status - " + statusCode;
                Log.d(TAG, msg);
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
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
                if (images.size() > 0) {
                    Image image = images.get(0);
                    imagePath = image.getPath();
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
}
