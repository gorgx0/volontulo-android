package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Offer;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;

import java.util.List;

public class OffersRealmAdapter extends BaseMockAdapter<Offer, OfferViewHolder> {

    public OffersRealmAdapter(Context context, final List<Offer> offers) {
        super(context, offers);
    }

    @Override
    protected int getLayoutResource(int viewType) {
        return R.layout.item_offer;
    }

    @Override
    protected OfferViewHolder createViewHolder(View item, int viewType) {
        return new OfferViewHolder(item);
    }
}
