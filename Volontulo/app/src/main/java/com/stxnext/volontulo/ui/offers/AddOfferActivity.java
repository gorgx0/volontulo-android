package com.stxnext.volontulo.ui.offers;

import android.os.Bundle;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;

public class AddOfferActivity extends VolontuloBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_add);
        init(R.string.add_offer);
    }
}
