package com.stxnext.volontulo.ui.offers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Offer;

import java.util.ArrayList;
import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OfferViewHolder> {
    private List<Offer> offerList = new ArrayList<>();

    public OffersAdapter() {
        offerList.add(new Offer("Xxx"));
        offerList.add(new Offer("Xyy"));
        offerList.add(new Offer("Yxx"));
        offerList.add(new Offer("Iee"));
        offerList.add(new Offer("Pee"));
    }

    @Override
    public OfferViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new OfferViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer, parent, false));
    }

    @Override
    public void onBindViewHolder(final OfferViewHolder holder, int position) {
        final Offer offerModel = offerList.get(position);
        holder.offerName.setText(offerModel.getOfferName());
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }
}
