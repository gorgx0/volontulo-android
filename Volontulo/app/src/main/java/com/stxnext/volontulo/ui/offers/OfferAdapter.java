package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;

import java.util.List;

public class OfferAdapter extends BaseMockAdapter<Offer, OfferViewHolder> {

    private int userId;

    public OfferAdapter(Context context) {
        super(context);
    }

    public OfferAdapter(Context context, int userId) {
        this(context);
        this.userId = userId;
    }

    @Override
    protected int getLayoutResource(int viewType) {
        return R.layout.item_offer;
    }

    @Override
    protected OfferViewHolder createViewHolder(View item, int viewType) {
        return new OfferViewHolder(item, userId);
    }

    public void swap(List<Offer> offers) {
        objects.clear();
        objects.addAll(offers);
        notifyDataSetChanged();
    }
}
