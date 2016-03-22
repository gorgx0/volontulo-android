package com.stxnext.volontulo.ui.offers;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;

import butterknife.OnClick;

public class OfferDetailsFragment extends VolontuloBaseFragment {
    @Override
    protected int fragmentLayoutResource() {
        return R.layout.fragment_offers_details;
    }

    @OnClick(R.id.button_step_out)
    public void doStepOut(View view) {
        Snackbar.make(view, "Zgłosiłeś się!!!", Snackbar.LENGTH_SHORT).show();
    }
}
