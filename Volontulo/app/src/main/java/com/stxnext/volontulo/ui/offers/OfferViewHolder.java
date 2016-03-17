package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Offer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

class OfferViewHolder extends RecyclerView.ViewHolder {
    @Bind(R.id.offer_avatar)
    protected ImageView offerImage;

    @Bind(R.id.offer_join)
    protected ImageButton offerJoinButton;

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
        Picasso.with(offerImage.getContext())
            .load(item.getOfferImageResource())
            .fit()
            .centerCrop()
            .into(offerImage);
        offerName.setText(item.getOfferName());
        offerPlace.setText(item.getOfferPlace());
        offerStart.setText(item.getFormattedStartTime());
        offerEnd.setText(item.getFormattedEndTime());
        if (item.isUserJoined()) {
            offerJoinButton.setImageResource(R.drawable.ic_offer_joined);
            offerJoinButton.setEnabled(false);
        } else {
            offerJoinButton.setImageResource(R.drawable.ic_offer_join);
            offerJoinButton.setEnabled(true);
        }
    }

    @OnClick(R.id.offer_content)
    void onItemClick(View clicked) {
        Context context = clicked.getContext();
        Toast.makeText(context, "DETAILS ACTION/OFFER", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, OfferDetailsActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.offer_join)
    void onJoinClick(View clicked) {
        Toast.makeText(clicked.getContext(), "JOIN OFFER", Toast.LENGTH_SHORT).show();
    }
}
