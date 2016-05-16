package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.api.SaveResponse;

import org.parceler.Parcels;

import retrofit2.Call;

public class OfferEditFragment extends OfferSaveFragment {

    @Override
    public void onPostAttach() {
        final Bundle arguments = getArguments();
        if (arguments != null) {
            final Parcelable offer = arguments.getParcelable(Offer.OFFER_OBJECT);
            formState = Parcels.unwrap(offer);
        }
    }

    @Override
    protected Call<SaveResponse> prepareCall(Offer offer, String token) {
        return VolontuloApp.api.updateOffer(token, offer.getId(), offer.getParams());
    }


    @Override
    protected void prepareRefresh() {
        final String preferenceFile = activity.getString(R.string.preference_file_name);
        final String offersRefresh = activity.getString(R.string.preference_offers_refresh);
        final SharedPreferences preferences = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(offersRefresh, true);
        editor.apply();
    }
}
