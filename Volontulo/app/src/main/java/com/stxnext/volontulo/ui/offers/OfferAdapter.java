package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;

import java.util.List;

import io.realm.Realm;

public class OfferAdapter extends BaseMockAdapter<Offer, OfferViewHolder> {

    private UserProfile profile;
    private Realm realm;
    private boolean highlightUserOffer;

    public OfferAdapter(Context context) {
        super(context);
    }

    public OfferAdapter(Context context, UserProfile profile) {
        this(context);
        this.profile = profile;
    }

    @Override
    protected int getLayoutResource(int viewType) {
        return R.layout.item_offer;
    }

    @Override
    protected OfferViewHolder createViewHolder(View item, int viewType) {
        final OfferViewHolder offerViewHolder = new OfferViewHolder(item, profile);
        offerViewHolder.setRealm(realm);
        offerViewHolder.setHighlightUserOffers(isHighlightUserOffer());
        return offerViewHolder;
    }

    public void swap(List<Offer> offers) {
        objects.clear();
        objects.addAll(offers);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        Offer offer = objects.get(position);
        return offer != null ? offer.getId() : -1;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public Realm getRealm() {
        return realm;
    }

    public boolean isHighlightUserOffer() {
        return highlightUserOffer;
    }

    public void setHighlightUserOffer(boolean highlightUserOffer) {
        this.highlightUserOffer = highlightUserOffer;
    }
}
