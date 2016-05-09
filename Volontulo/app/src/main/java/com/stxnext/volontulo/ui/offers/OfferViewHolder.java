package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.OnClick;

class OfferViewHolder extends BaseViewHolder<Offer> {

    private int id;
    private String imagePath;

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
    private int userId;

    public OfferViewHolder(View itemView) {
        super(itemView);
    }

    public OfferViewHolder(View itemView, int userId) {
        this(itemView);
        this.userId = userId;
    }

    @OnClick(R.id.offer_content)
    void onItemClick(View clicked) {
        Context context = clicked.getContext();
        Intent intent = new Intent(context, OfferDetailsActivity.class);
        intent.putExtra(Offer.OFFER_ID, id);
        intent.putExtra(Offer.OFFER_OBJECT, Parcels.wrap(objectBinded));
        if (!TextUtils.isEmpty(imagePath)) {
            intent.putExtra(Offer.IMAGE_PATH, imagePath);
        }
        context.startActivity(intent);
    }

    @OnClick(R.id.offer_join)
    void onJoinClick(View clicked) {
        Snackbar.make(clicked.getRootView(), "JOIN OFFER", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBind(Offer item) {
        id = item.getId();
        if (item.hasImage()) {
            imagePath = item.retrieveImagePath();
            Picasso.with(offerImage.getContext())
                    .load(imagePath)
                    .fit()
                    .centerCrop()
                    .into(offerImage);
        } else {
            offerImage.setImageResource(0);
        }
        offerName.setText(item.getTitle());
        offerPlace.setText(item.getLocation());
        offerStart.setText(item.getStartedAt());
        offerEnd.setText(item.getFinishedAt());
        if (item.isUserJoined(userId)) {
            offerJoinButton.setImageResource(R.drawable.ic_offer_joined);
            offerJoinButton.setEnabled(false);
        } else {
            offerJoinButton.setImageResource(R.drawable.ic_offer_join);
            offerJoinButton.setEnabled(true);
        }
    }
}
