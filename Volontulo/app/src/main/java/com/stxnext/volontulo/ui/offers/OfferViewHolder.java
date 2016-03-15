package com.stxnext.volontulo.ui.offers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Offer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

class OfferViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.offer_avatar)
    protected ImageView offerImage;

    @Bind(R.id.offer_name)
    protected TextView offerName;

    @Bind(R.id.offer_place)
    protected TextView offerPlace;

    @Bind(R.id.offer_start_time)
    protected TextView offerStart;

    @Bind(R.id.offer_end_time)
    protected TextView offerEnd;

    public OfferViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void update(final Offer item) {
        offerImage.setImageResource(item.getOfferImageResource());
        offerName.setText(item.getOfferName());
        offerPlace.setText(item.getOfferPlace());
        offerStart.setText(item.getFormattedStartTime());
        offerEnd.setText(item.getFormattedEndTime());
    }

    @OnClick(R.id.offer_content)
    void onItemClick(View clicked) {
        Toast.makeText(clicked.getContext(), "DETAILS ACTION/OFFER", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.offer_join)
    void onJoinClick(View clicked) {
        Toast.makeText(clicked.getContext(), "JOIN OFFER", Toast.LENGTH_SHORT).show();
    }
}
