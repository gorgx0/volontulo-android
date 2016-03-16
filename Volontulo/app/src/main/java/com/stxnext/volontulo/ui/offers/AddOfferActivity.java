package com.stxnext.volontulo.ui.offers;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class AddOfferActivity extends VolontuloBaseActivity {
    @Bind(R.id.offer_name_layout)
    TextInputLayout offerNameLayout;

    @Bind(R.id.offer_name)
    EditText offerName;

    @Bind(R.id.offer_place_layout)
    TextInputLayout offerPlaceLayout;

    @Bind(R.id.offer_place)
    EditText offerPlace;

    @Bind(R.id.offer_description_layout)
    TextInputLayout offerDescriptionLayout;

    @Bind(R.id.offer_description)
    EditText offerDescription;

    @Bind(R.id.offer_time_requirement_layout)
    TextInputLayout offerTimeRequirementLayout;

    @Bind(R.id.offer_time_requirement)
    EditText offerTimeRequirement;

    @Bind(R.id.offer_benefits_layout)
    TextInputLayout offerBenefitsLayout;

    @Bind(R.id.offer_benefits)
    EditText offerBenefits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_add);
        init(R.string.add_offer);
    }

    @OnClick(R.id.add_offer)
    void onOfferAdd(View clicked) {
        if (!validateFields()) {
            return;
        }
        finish();
    }

    private boolean validateFields() {
        boolean result = true;
        if (TextUtils.isEmpty(offerName.getText())) {
            offerNameLayout.setError("Określ, jak oferta będzie nazywać się w systemie, staraj się być dokładnym.");
            result = false;
        }
        if (TextUtils.isEmpty(offerPlace.getText())) {
            offerPlaceLayout.setError("Określ, w jakim miejscu odbywać się będzie wolontariat.");
            result = false;
        }
        if (TextUtils.isEmpty(offerDescription.getText())) {
            offerDescriptionLayout.setError("Postaraj się dokładnie opisać akcję.");
            result = false;
        }
        if (TextUtils.isEmpty(offerTimeRequirement.getText())) {
            offerTimeRequirementLayout.setError("Określ wymagania czasowe - ile godzin pracy w ciągu dnia wymaga dana akcja.");
            result = false;
        }
        if (TextUtils.isEmpty(offerBenefits.getText())) {
            offerBenefitsLayout.setError("Określ korzyści płynące z udziału w akcji.");
            result = false;
        }
        return result;
    }
}
