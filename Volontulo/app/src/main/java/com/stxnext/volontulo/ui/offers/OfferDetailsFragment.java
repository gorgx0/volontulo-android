package com.stxnext.volontulo.ui.offers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.JoinResponse;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.logic.session.SessionManager;

import org.parceler.Parcels;

import butterknife.Bind;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferDetailsFragment extends VolontuloBaseFragment {

    public static final String TAG = "RETROFIT-TEST";
    public static final int REQUEST_EDIT = 1;

    @Bind(R.id.text_title)
    TextView title;

    @Bind(R.id.text_location)
    TextView location;

    @Bind(R.id.text_duration)
    TextView duration;

    @Bind(R.id.text_description)
    TextView description;

    @Bind(R.id.text_benefits)
    TextView benefits;

    @Bind(R.id.text_requirements)
    TextView requirements;

    @Bind(R.id.text_time_commitment)
    TextView timeCommitment;

    @Bind(R.id.text_organization)
    TextView organization;

    @DrawableRes
    private int imageResource;

    private String imagePath;
    private MenuItem itemJoined, itemEdit;
    private Realm realm;
    private int id;
    private Offer offer;
    private boolean joinedVisible = false, editVisible;
    private UserProfile profile;
    private Bundle args;

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_offers_details;
    }

    @Override
    public int getImageResource() {
        return imageResource;
    }

    private void fillData(Offer offer) {
        title.setText(offer.getTitle());
        setToolbarTitle(offer.getTitle());
        location.setText(offer.getLocation());
        duration.setText(offer.getDuration(getString(R.string.now), getString(R.string.to_set)));
        description.setText(offer.getDescription());
        requirements.setText(offer.getRequirements());
        benefits.setText(offer.getBenefits());
        timeCommitment.setText(offer.getTimeCommitment());
        organization.setText(offer.getOrganization().getName());
        if (offer.hasImage()) {
            imagePath = offer.retrieveImagePath();
            imageResource = 0;
        }
        if (offer.isUserJoined(profile.getUser().getId())) {
            joinedVisible = true;
        } else if (offer.canBeJoined(profile)) {
            requestFloatingActionButton();
        }
        if (offer.canBeEdit(profile)) {
            editVisible = true;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.offer_details_menu, menu);
        itemJoined = menu.findItem(R.id.action_offer_joined);
        itemJoined.setVisible(joinedVisible);
        itemEdit = menu.findItem(R.id.action_offer_edit);
        itemEdit.setVisible(editVisible);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        args = getArguments();
        id = args.getInt(Offer.OFFER_ID, 0);
        offer = Parcels.unwrap(args.getParcelable(Offer.OFFER_OBJECT));
        if (offer != null) {
            Log.d(TAG, "FROM-PARCEL " + offer.toString());
            if (offer.hasImage()) {
                imageResource = args.getInt(Offer.IMAGE_RESOURCE, 0);
                if (args.containsKey(Offer.IMAGE_PATH)) {
                    imagePath = args.getString(Offer.IMAGE_PATH);
                }
            }
        }
    }

    @Override
    protected void onPostCreateView(View root) {
        realm = Realm.getDefaultInstance();
        offer.load();
        int userProfileId = SessionManager.getInstance(getActivity()).getUserProfile().getId();
        profile = realm.where(UserProfile.class).equalTo("id", userProfileId).findFirst();
        profile.load();
        fillData(offer);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

    @Override
    protected void onFabClick(final FloatingActionButton button) {
        final SessionManager sessionManager = SessionManager.getInstance(getActivity());

        final UserProfile userProfile = sessionManager.getUserProfile();
        final Call<JoinResponse> call = VolontuloApp.api.joinOffer(offer.getId(), sessionManager.getSessionToken(), userProfile.getEmail(), userProfile.getPhoneNo(), userProfile.getUser().getUsername());
        call.enqueue(new Callback<JoinResponse>() {
            @Override
            public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                final Context context = getContext();
                if (response.isSuccessful()) {
                    button.setVisibility(View.GONE);
                    itemJoined.setVisible(true);
                    final User user = realm.where(User.class).equalTo("id", sessionManager.getUserProfile().getUser().getId()).findFirst();
                    offer.getVolunteers().add(user);
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(offer);
                    realm.commitTransaction();
                    View view = getView();
                    if (view != null) {
                        Snackbar.make(view, context.getString(R.string.offer_joined_message), Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, context.getString(R.string.error_something_wrong) + " '" + response.message() + "'", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JoinResponse> call, Throwable t) {
                String msg = "[FAILURE] message - " + t.getMessage();
                Toast.makeText(getContext(), R.string.error_connection, Toast.LENGTH_SHORT).show();
                Log.d(TAG, msg);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_offer_edit:
                final Intent intent = new Intent(getActivity(), OfferEditActivity.class);
                intent.putExtra(Offer.OFFER_OBJECT, Parcels.wrap(offer));
                startActivityForResult(intent, REQUEST_EDIT);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_EDIT) {
            final FragmentActivity activity = getActivity();
            final Parcelable offer = data.getParcelableExtra(Offer.OFFER_OBJECT);
            Intent intent = new Intent();
            intent.putExtra(Offer.OFFER_OBJECT, offer);
            activity.setResult(Activity.RESULT_OK, intent);
            activity.finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
