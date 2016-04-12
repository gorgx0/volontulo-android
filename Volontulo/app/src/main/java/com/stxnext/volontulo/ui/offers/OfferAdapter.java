package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;

import java.util.List;

public class OfferAdapter extends BaseMockAdapter<Offer, OfferViewHolder> {

    public OfferAdapter(Context context, List<Offer> results) {
        super(context, R.layout.item_offer, results);
    }

    @Override
    protected OfferViewHolder createViewHolder(View item) {
        return new OfferViewHolder(item);
    }
}
