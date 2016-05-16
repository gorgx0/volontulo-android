package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.content.SharedPreferences;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.api.SaveResponse;

import retrofit2.Call;

public class OfferCreateFragment extends OfferSaveFragment {

    @Override
    public void onPostAttach() {
        formState = new Offer();
    }

    @Override
    protected Call<SaveResponse> prepareCall(Offer offer, String token) {
        return VolontuloApp.api.createOffer(token, offer.getParams());
    }

    @Override
    protected void prepareRefresh() {
        final String preferenceFile = activity.getString(R.string.preference_file_name);
        final String offersRefresh = activity.getString(R.string.preference_offers_refresh);
        final SharedPreferences preferences = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        String offersPosition = activity.getString(R.string.preference_offers_position);
        editor.putBoolean(offersRefresh, true);
        editor.remove(offersPosition);
        editor.apply();
    }
}
