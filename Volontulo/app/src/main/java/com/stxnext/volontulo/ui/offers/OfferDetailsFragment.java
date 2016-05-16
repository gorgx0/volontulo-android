package com.stxnext.volontulo.ui.offers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.logic.offer.JoinOffer;
import com.stxnext.volontulo.logic.session.SessionManager;

import org.parceler.Parcels;

import butterknife.BindView;
import io.realm.Realm;
import timber.log.Timber;

public class OfferDetailsFragment extends VolontuloBaseFragment implements JoinOffer.JoinOfferResult {

    public static final int REQUEST_EDIT = 1;

    @BindView(R.id.text_title)
    TextView title;

    @BindView(R.id.text_location)
    TextView location;

    @BindView(R.id.text_duration)
    TextView duration;

    @BindView(R.id.text_description)
    TextView description;

    @BindView(R.id.text_benefits)
    TextView benefits;

    @BindView(R.id.text_requirements)
    TextView requirements;

    @BindView(R.id.text_time_commitment)
    TextView timeCommitment;

    @BindView(R.id.text_organization)
    TextView organization;

    private String imagePath;
    private MenuItem itemJoined;
    private Realm realm;
    private Offer offer;
    private boolean joinedVisible = false, editVisible;
    private UserProfile profile;
    private FloatingActionButton joinButton;

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_offers_details;
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
        MenuItem itemEdit = menu.findItem(R.id.action_offer_edit);
        itemEdit.setVisible(editVisible);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle args = getArguments();
        offer = Parcels.unwrap(args.getParcelable(Offer.OFFER_OBJECT));
        if (offer != null) {
            Timber.d("FROM-PARCEL %s", offer.toString());
            if (offer.hasImage()) {
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
        profile = realm.where(UserProfile.class).equalTo(UserProfile.FIELD_ID, userProfileId).findFirst();
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
        joinButton = button;
        final SessionManager sessionManager = SessionManager.getInstance(getActivity());

        JoinOffer joinOffer = new JoinOffer(realm, sessionManager.getSessionToken());
        joinOffer.registerJoinOfferResult(this);
        joinOffer.execute(sessionManager.getUserProfile(), offer);
    }

    @Override
    public void onOfferJoined(boolean result, String message) {
        final Context context = getContext();
        if (result) {
            joinButton.setVisibility(View.GONE);
            itemJoined.setVisible(true);
            View view = getView();
            if (view != null) {
                Snackbar.make(view, context.getString(R.string.offer_joined_message), Snackbar.LENGTH_SHORT).show();
            }
        } else {
            final String error = context.getString(R.string.error_something_wrong) + " '" + message + "'";
            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
            Timber.e(error);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_offer_edit:
                final Intent intent = new Intent(getActivity(), OfferSaveActivity.class);
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
