package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Ofer;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;

import java.util.List;

public class OffersRealmAdapter extends BaseMockAdapter<Ofer, OfferViewRealmHolder> {

    public OffersRealmAdapter(Context context, final List<Ofer> ofers) {
        super(context, ofers);
    }

    @Override
    protected int getLayoutResource(int viewType) {
        return R.layout.item_offer;
    }

    @Override
    protected OfferViewRealmHolder createViewHolder(View item, int viewType) {
        return new OfferViewRealmHolder(item);
    }
}
