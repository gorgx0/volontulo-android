package com.stxnext.volontulo.ui.offers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Offer;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

class MockOffersAdapter extends RecyclerView.Adapter<OfferViewHolder> {
    private List<Offer> offerList = new ArrayList<>();

    public MockOffersAdapter() {
        offerList.add(Offer.mockOffer("Oferta 1", "Poznań", DateTime.now(), DateTime.now().plusDays(7), R.drawable.apple));
        offerList.add(Offer.mockOffer("Oferta 2", "Polska", DateTime.now().plusMonths(3), DateTime.now().plusMonths(3).plusDays(7), R.drawable.breakfast_free));
        offerList.add(Offer.mockOffer("Oferta 3", "Warszawa", DateTime.now(), DateTime.now().plusDays(7), R.drawable.cookie));
        offerList.add(Offer.mockOffer("Oferta 4", "Leszno", DateTime.now().minusDays(1), DateTime.now().plusDays(7), R.drawable.ice));
        offerList.add(Offer.mockOffer("Oferta 5", "Wrocław", DateTime.now().minusDays(1), DateTime.now().plusDays(7), R.drawable.join));
        offerList.add(Offer.mockOffer("Oferta 6", "Poznań", DateTime.now().minusWeeks(1), DateTime.now().plusWeeks(2), R.drawable.oscar));
    }

    @Override
    public OfferViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        return new OfferViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer, parent, false));
    }

    @Override
    public void onBindViewHolder(final OfferViewHolder holder, int position) {
        final Offer offerModel = offerList.get(position);
        holder.update(offerModel);
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }
}
