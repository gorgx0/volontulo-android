package com.stxnext.volontulo.ui.map;

import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Ofer;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import org.joda.time.DateTime;

import java.util.List;

class MockOffersMapAdapter extends BaseMockAdapter<Ofer, BaseViewHolder<Ofer>> {

    public MockOffersMapAdapter(Context context) {
        super(context, R.layout.item_offer);
        objects.add(Ofer.mockPlace("Oferta 1", "Poznań", new LatLng(52.408333, 16.934167), DateTime.now(), DateTime.now().plusDays(7), R.drawable.apple, false));
        objects.add(Ofer.mockPlace("Oferta 2", "Polska", new LatLng(52.212222, 21.098333), DateTime.now().plusMonths(3), DateTime.now().plusMonths(3).plusDays(7), R.drawable.breakfast_free, false));
        objects.add(Ofer.mockPlace("Oferta 3", "Warszawa", new LatLng(52.232222, 21.008333), DateTime.now(), DateTime.now().plusDays(7), R.drawable.cookie, true));
        objects.add(Ofer.mockPlace("Oferta 4", "Leszno", new LatLng(51.845833, 16.580556), DateTime.now().minusDays(1), DateTime.now().plusDays(7), R.drawable.ice, false));
        objects.add(Ofer.mockPlace("Oferta 5", "Wrocław", new LatLng(51.11, 17.022222), DateTime.now().minusDays(1), DateTime.now().plusDays(7), R.drawable.join, false));
        objects.add(Ofer.mockPlace("Oferta 6", "Poznań", new LatLng(52.408333, 16.934167), DateTime.now().minusWeeks(1), DateTime.now().plusWeeks(2), R.drawable.oscar, true));
    }

    @Override
    protected BaseViewHolder<Ofer> createViewHolder(View item) {
        return new BaseViewHolder<Ofer>(item) {
            @Override
            public void onBind(Ofer model) {
            }
        };
    }

    List<Ofer> getObjects() {
        return objects;
    }
}
