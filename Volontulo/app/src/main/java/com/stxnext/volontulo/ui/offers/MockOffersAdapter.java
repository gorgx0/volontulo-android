package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Offer;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;

import org.joda.time.DateTime;

class MockOffersAdapter extends BaseMockAdapter<Offer, OfferViewHolder> {

    public MockOffersAdapter(Context context) {
        super(context, R.layout.item_offer);
        objects.add(Offer.mock("Oferta 1", "Poznań", DateTime.now(), DateTime.now().plusDays(7), R.drawable.apple, false));
        objects.add(Offer.mock("Oferta 2", "Polska", DateTime.now().plusMonths(3), DateTime.now().plusMonths(3).plusDays(7), R.drawable.breakfast_free, false));
        objects.add(Offer.mock("Oferta 3", "Warszawa", DateTime.now(), DateTime.now().plusDays(7), R.drawable.cookie, true));
        objects.add(Offer.mock("Oferta 4", "Leszno", DateTime.now().minusDays(1), DateTime.now().plusDays(7), R.drawable.ice, false));
        objects.add(Offer.mock("Oferta 5", "Wrocław", DateTime.now().minusDays(1), DateTime.now().plusDays(7), R.drawable.join, false));
        objects.add(Offer.mock("Oferta 6", "Poznań", DateTime.now().minusWeeks(1), DateTime.now().plusWeeks(2), R.drawable.oscar, true));
    }

    @Override
    protected OfferViewHolder createViewHolder(View item) {
        return new OfferViewHolder(item);
    }
}
