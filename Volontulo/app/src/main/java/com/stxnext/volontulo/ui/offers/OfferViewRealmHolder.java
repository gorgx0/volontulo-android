package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Ofer;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

class OfferViewRealmHolder extends BaseViewHolder<Ofer> {
    @BindView(R.id.offer_avatar)
    protected ImageView offerImage;

    @BindView(R.id.offer_join)
    protected ImageButton offerJoinButton;

    @BindView(R.id.offer_name)
    protected TextView offerName;

    @BindView(R.id.offer_place)
    protected TextView offerPlace;

    @BindView(R.id.offer_start_time)
    protected TextView offerStart;

    @BindView(R.id.offer_end_time)
    protected TextView offerEnd;

    private int imageResource;

    public OfferViewRealmHolder(View itemView) {
        super(itemView);
    }

    @OnClick(R.id.offer_content)
    void onItemClick(View clicked) {
        Context context = clicked.getContext();
        Intent intent = new Intent(context, OfferDetailsActivity.class);
        intent.putExtra(Ofer.IMAGE_RESOURCE, imageResource);
        context.startActivity(intent);
    }

    @OnClick(R.id.offer_join)
    void onJoinClick(View clicked) {
        Snackbar.make(clicked.getRootView(), "JOIN OFFER", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onBind(Ofer item) {
        if (item.getImageResource() > 0) {
            Picasso.with(offerImage.getContext())
                    .load(item.getImageResource())
                    .fit()
                    .centerCrop()
                    .into(offerImage);
        } else {
            Picasso.with(offerImage.getContext())
                    .load(item.getImagePath())
                    .fit()
                    .centerCrop()
                    .into(offerImage);
        }
        offerName.setText(item.getName());
        offerPlace.setText(item.getPlaceName());
        offerStart.setText(item.getFormattedStartTime());
        offerEnd.setText(item.getFormattedEndTime());
        imageResource = item.getImageResource();
        if (item.isUserJoined()) {
            offerJoinButton.setImageResource(R.drawable.ic_offer_joined);
            offerJoinButton.setEnabled(false);
        } else {
            offerJoinButton.setImageResource(R.drawable.ic_offer_join);
            offerJoinButton.setEnabled(true);
        }
    }
}
