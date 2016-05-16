package com.stxnext.volontulo.logic.offer;

import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.api.JoinResponse;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.api.UserProfile;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class JoinOffer {

    private Realm realm;
    private String token;
    private JoinOfferResult joinOfferResult;

    public interface JoinOfferResult {
        void onOfferJoined(boolean result, String message);
    }

    public JoinOffer(Realm realm, String token) {
        this.realm = realm;
        this.token = token;
    }

    public void registerJoinOfferResult(JoinOfferResult result) {
        joinOfferResult = result;
    }

    public void execute(final UserProfile userProfile, final Offer offer) {
        final Call<JoinResponse> call = VolontuloApp.api.joinOffer(offer.getId(), token, userProfile.getEmail(), userProfile.getPhoneNo(), userProfile.getUser().getUsername());
        call.enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                boolean result;
                String msg = "";
                if (response.isSuccessful()) {
                    final User user = realm.where(User.class).equalTo(User.FIELD_ID, userProfile.getUser().getId()).findFirst();
                    realm.beginTransaction();
                    offer.getVolunteers().add(user);
                    realm.copyToRealmOrUpdate(offer);
                    realm.commitTransaction();
                    result = true;
                } else {
                    result = false;
                    msg = response.message();
                }
                if (joinOfferResult != null) {
                    joinOfferResult.onOfferJoined(result, msg);
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                String msg = "[FAILURE] message - " + t.getMessage();
                if (joinOfferResult != null) {
                    joinOfferResult.onOfferJoined(false, t.getMessage());
                }
                Timber.d(msg);
            }
        });
    }
}
