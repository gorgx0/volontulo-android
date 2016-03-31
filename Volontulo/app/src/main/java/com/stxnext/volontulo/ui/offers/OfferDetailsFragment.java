package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.model.Offer;

import butterknife.OnClick;

public class OfferDetailsFragment extends VolontuloBaseFragment {

    @DrawableRes
    private int imageResource;

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
    }
}
