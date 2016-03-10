package com.stxnext.volontulo.ui.offers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stxnext.volontulo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

class OfferViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.offer_name)
    TextView offerName;

    public OfferViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
