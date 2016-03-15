package com.stxnext.volontulo.ui.offers;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;

import butterknife.OnClick;

public class OffersDetailsActivity extends VolontuloBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_details);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_step_out)
    public void doStepOut(View view) {
        Snackbar.make(view, "Zgłosiłeś się!!!", Snackbar.LENGTH_SHORT).show();
    }

}
