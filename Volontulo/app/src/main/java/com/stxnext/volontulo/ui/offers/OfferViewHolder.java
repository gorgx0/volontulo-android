package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.logic.offer.JoinOffer;
import com.stxnext.volontulo.logic.session.SessionManager;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import timber.log.Timber;

class OfferViewHolder extends BaseViewHolder<Offer> implements JoinOffer.JoinOfferResult {

    private String imagePath;

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
    private UserProfile profile;
    private Realm realm;
    private boolean highlightUserOffers;

    public OfferViewHolder(View itemView) {
        super(itemView);
    }

    public OfferViewHolder(View itemView, UserProfile userProfile) {
        this(itemView);
        profile = userProfile;
    }

    @OnClick(R.id.offer_content)
    void onItemClick(View clicked) {
        Context context = clicked.getContext();
        final String preferenceFile = context.getString(R.string.preference_file_name);
        final String offersPosition = context.getString(R.string.preference_offers_position);
        final SharedPreferences preferences = context.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
        preferences.edit().
                putInt(offersPosition, position)
                .apply();
        Intent intent = new Intent(context, OfferDetailsActivity.class);
        intent.putExtra(Offer.OFFER_ID, objectBinded.getId());
        intent.putExtra(Offer.OFFER_OBJECT, Parcels.wrap(objectBinded));
        if (!TextUtils.isEmpty(imagePath)) {
            intent.putExtra(Offer.IMAGE_PATH, imagePath);
        }
        context.startActivity(intent);
    }

    @OnClick(R.id.offer_join)
    void onJoinClick(View clicked) {
        SessionManager sessionManager = SessionManager.getInstance(clicked.getContext());

        JoinOffer joinOffer = new JoinOffer(realm, sessionManager.getSessionToken());
        joinOffer.registerJoinOfferResult(this);
        joinOffer.execute(profile, objectBinded);
    }

    @Override
    public void onOfferJoined(boolean result, String message) {
        final Context context = offerJoinButton.getContext();
        if (result) {
            boolean published = Offer.OFFER_STATUS_PUBLISHED.equals(objectBinded.getOfferStatus());
            if (published) {
                offerJoinButton.setImageResource(R.drawable.ic_offered_joined_white);
            } else {
                offerJoinButton.setImageResource(R.drawable.ic_offer_joined);
            }
            offerJoinButton.setEnabled(false);
            final View view = offerJoinButton.getRootView();
            if (view != null) {
                Snackbar.make(view, context.getString(R.string.offer_joined_message), Snackbar.LENGTH_LONG).show();
            }
        } else {
            final String error = context.getString(R.string.error_something_wrong) + " '" + message + "'";
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            Timber.e(error);
        }
    }

    @Override
    public void onBind(Offer item) {
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
        offerName.setText("[" + item.getId() + "]: " + item.getTitle());
        offerPlace.setText(item.getLocation());
        offerStart.setText(item.getStartedAt());
        offerEnd.setText(item.getFinishedAt());
        if (item.canBeEdit(profile) && isHighlightUserOffers()) {
            offerName.setTextColor(Color.RED);
            offerName.setText(">>> " + item.getTitle());
        } else {
            offerName.setTextColor(Color.BLACK);
        }
        boolean published = Offer.OFFER_STATUS_PUBLISHED.equals(item.getOfferStatus());
        if (item.isUserJoined(profile.getUser().getId())) {
            if (published) {
                offerJoinButton.setImageResource(R.drawable.ic_offered_joined_white);
            } else {
                offerJoinButton.setImageResource(R.drawable.ic_offer_joined);
            }
            offerJoinButton.setEnabled(false);
        } else {
            if (published) {
                offerJoinButton.setImageResource(R.drawable.ic_offer_join_white);
            } else {
                offerJoinButton.setImageResource(R.drawable.ic_offer_join);
            }
            offerJoinButton.setEnabled(true);
        }
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public Realm getRealm() {
        return realm;
    }

    public void setHighlightUserOffers(boolean highlightUserOffers) {
        this.highlightUserOffers = highlightUserOffers;
    }

    public boolean isHighlightUserOffers() {
        return highlightUserOffers;
    }
}
