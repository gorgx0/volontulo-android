
package com.stxnext.volontulo.ui.offers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.squareup.picasso.Picasso;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.api.Organization;
import com.stxnext.volontulo.api.SaveError;
import com.stxnext.volontulo.api.SaveResponse;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.logic.session.SessionManager;
import com.stxnext.volontulo.ui.utils.BaseTextWatcher;

import org.parceler.Parcels;

import java.io.IOException;
import java.lang.annotation.Annotation;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import timber.log.Timber;

public abstract class OfferSaveFragment extends VolontuloBaseFragment {
    private static final int REQUEST_IMAGE = 0x1011;
    private static final String KEY_OFFER_FORM = "offer-form";
    public static final LatLngBounds POLAND_BOUNDING_BOX = new LatLngBounds(
            new LatLng(49.0821066, 14.1972837),
            new LatLng(54.8263969, 23.6091250)
    );

    @BindView(R.id.offer_name_layout) TextInputLayout offerNameLayout;
    @BindView(R.id.offer_name) EditText offerName;
    @BindView(R.id.offer_description_layout) TextInputLayout offerDescriptionLayout;
    @BindView(R.id.offer_description) EditText offerDescription;
    @BindView(R.id.offer_time_requirement_layout) TextInputLayout offerTimeRequirementLayout;
    @BindView(R.id.offer_time_requirement) EditText offerTimeRequirement;
    @BindView(R.id.offer_benefits_layout) TextInputLayout offerBenefitsLayout;
    @BindView(R.id.offer_benefits) EditText offerBenefits;
    @BindView(R.id.offer_requirements) EditText offerRequirements;
    @BindView(R.id.offer_thumbnail_card) View offerThumbnailCard;
    @BindView(R.id.offer_thumbnail) ImageView offerThumbnail;
    @BindView(R.id.offer_thumbnail_name) TextView offerThumbnailName;
    @BindView(R.id.scroller) ScrollView scrollView;
    @BindView(R.id.place_autocomplete_result_switcher) ViewSwitcher placeAutocompleteResultSwitcher;
    private SupportPlaceAutocompleteFragment placeFragment;

    protected Offer formState;
    protected FragmentActivity activity;

    protected abstract void prepareRefresh();

    protected abstract Call<SaveResponse> prepareCall(Offer offer, String token);

    public abstract void onPostAttach();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_offer_save;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
        onPostAttach();
    }

    @Override
    protected void onPostCreateView(View root) {
        setHasOptionsMenu(true);
        offerName.addTextChangedListener(new OfferObjectUpdater(offerName.getId(), formState));
        offerDescription.addTextChangedListener(new OfferObjectUpdater(offerDescription.getId(), formState));
        offerBenefits.addTextChangedListener(new OfferObjectUpdater(offerBenefits.getId(), formState));
        offerTimeRequirement.addTextChangedListener(new OfferObjectUpdater(offerTimeRequirement.getId(), formState));
        offerRequirements.addTextChangedListener(new OfferObjectUpdater(offerRequirements.getId(), formState));
        final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        placeFragment = (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        placeFragment.setHint(getString(R.string.offer_place));
        placeFragment.setBoundsBias(POLAND_BOUNDING_BOX);
        placeFragment.setFilter(new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_GEOCODE).build());
        placeFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(final Place place) {
                formState.setLocationNameAndPosition(place);
                mapFragment.getMapAsync(new ThumbnailMap(place.getLatLng(), place.getName()));
                placeAutocompleteResultSwitcher.showNext();
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(activity, String.format("Error@place selector: %s", status), Toast.LENGTH_LONG).show();
            }
        });

        fillFormFrom(formState);
    }
    private static class OfferObjectUpdater extends BaseTextWatcher {
        @IdRes
        private final int editTextId;

        private final Offer updated;

        OfferObjectUpdater(@IdRes int id, Offer model) {
            editTextId = id;
            updated = model;
        }
        @Override
        public void afterTextChanged(Editable s) {
            final String result = s.toString();
            switch (editTextId) {
                case R.id.offer_name:
                    updated.setTitle(result);
                    break;

                case R.id.offer_description:
                    updated.setDescription(result);
                    break;

                case R.id.offer_benefits:
                    updated.setBenefits(result);
                    break;

                case R.id.offer_time_requirement:
                    updated.setTimeCommitment(result);
                    break;

                case R.id.offer_requirements:
                    updated.setRequirements(result);
                    break;
            }
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.attach_image_menu, menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            final Uri selectedImage = data.getData();
            loadImageFromUri(selectedImage);
            formState.applyImagePath(selectedImage);
            scrollView.post(new Runnable() {
                @Override
                public void run() {
                    scrollView.fullScroll(View.FOCUS_DOWN);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_attach_file:
                startActivityForResult(
                        new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                        REQUEST_IMAGE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_OFFER_FORM, Parcels.wrap(formState));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            formState = Parcels.unwrap(savedInstanceState.getParcelable(Offer.OFFER_OBJECT));
            Timber.d("FROM-PARCEL %s", formState.toString());
            fillFormFrom(formState);
        }
    }

    private void fillFormFrom(final Offer formState) {
        offerName.setText(formState.getTitle());
        placeFragment.setText(formState.getLocation());
        offerDescription.setText(formState.getDescription());
        offerTimeRequirement.setText(formState.getTimeCommitment());
        offerBenefits.setText(formState.getBenefits());
        offerRequirements.setText(formState.getRequirements());
        if (!TextUtils.isEmpty(formState.retrieveImagePath())) {
            loadImageFromUri(Uri.parse(formState.retrieveImagePath()));
        } else {
            unloadImage();
        }
        if (!TextUtils.isEmpty(formState.getLocation())) {
            final LatLng position = new LatLng(formState.getLocationLatitude(), formState.getLocationLongitude());
            final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(new ThumbnailMap(position, formState.getLocation()));
            placeAutocompleteResultSwitcher.showNext();
        }
    }

    @OnClick(R.id.close_map)
    void onMapClose() {
        placeAutocompleteResultSwitcher.showNext();
    }

    @OnClick(R.id.offer_thumbnail_delete)
    void onThumbnailDelete() {
        unloadImage();
    }

    private void unloadImage() {
        offerThumbnail.setImageDrawable(null);
        offerThumbnailName.setText("");
        offerThumbnailCard.setVisibility(View.GONE);
    }

    @OnClick(R.id.add_offer)
    void onOfferAdd() {
        if (!validateFields()) {
            return;
        }
        saveOffer(formState);
    }

    private void saveOffer(final Offer offer) {
        final SessionManager manager = SessionManager.getInstance(activity);
        final Realm realm = Realm.getDefaultInstance();
        final UserProfile userProfile = realm.where(UserProfile.class).equalTo(UserProfile.FIELD_ID, manager.getUserProfile().getId()).findFirst();
        if (userProfile == null || userProfile.getOrganizations().size() == 0) {
            return;
        }
        realm.close();

        final Organization organization = userProfile.getOrganizations().first();
        if (organization == null) {
            Toast.makeText(activity, R.string.error_organization_lack, Toast.LENGTH_LONG).show();
        } else {
            if (offer.getOrganization() == null) {
                offer.setOrganization(organization);
            }
        }
        offer.setOrganization(organization);
        Call<SaveResponse> call = prepareCall(offer, manager.getSessionToken());
        call.enqueue(new Callback<SaveResponse>() {
            @Override
            public void onResponse(Call<SaveResponse> call, Response<SaveResponse> response) {
                Timber.d("RESPONSE");
                if (response.isSuccessful()) {
                    Timber.d("SUCCESSFUL");
                    final SaveResponse saved = response.body();
                    offer.setId(saved.getId());
                    offer.setUrl(saved.getUrl());
                    offer.setRequirements(saved.getRequirements());
                    offer.setTimePeriod(saved.getTimePeriod());
                    offer.setStatusOld(saved.getStatusOld());
                    offer.setRecruitmentStatus(saved.getRecruitmentStatus());
                    offer.setActionStatus(saved.getActionStatus());
                    final Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(offer);
                    realm.commitTransaction();
                    realm.close();
                } else {
                    Converter<ResponseBody, SaveError> converter = VolontuloApp.retrofit.responseBodyConverter(SaveError.class, new Annotation[0]);
                    try {
                        final SaveError error = converter.convert(response.errorBody());
                        Timber.d("ERROR: %s", error.getDetail());
                    } catch (IOException e) {
                        Timber.d(e, "EXCEPTION: %s", e.getMessage());
                    }
                }
                prepareRefresh();
                activity.setResult(Activity.RESULT_OK, new Intent());
                activity.finish();
            }

            @Override
            public void onFailure(Call<SaveResponse> call, Throwable t) {
                Timber.d("FAILURE");
                activity.finish();
            }
        });
    }

    private boolean validateFields() {
        boolean result = true;
        if (TextUtils.isEmpty(offerName.getText())) {
            offerNameLayout.setError(getString(R.string.offer_name_validate));
            result = false;
        }
        if (TextUtils.isEmpty(formState.getLocation())) {
            Toast.makeText(activity, getString(R.string.offer_place_validate), Toast.LENGTH_LONG).show();
            result = false;
        }
        if (TextUtils.isEmpty(offerDescription.getText())) {
            offerDescriptionLayout.setError(getString(R.string.offer_description_validate));
            result = false;
        }
        if (TextUtils.isEmpty(offerTimeRequirement.getText())) {
            offerTimeRequirementLayout.setError(getString(R.string.offer_time_requirement_validate));
            result = false;
        }
        if (TextUtils.isEmpty(offerBenefits.getText())) {
            offerBenefitsLayout.setError(getString(R.string.offer_benefits_validate));
            result = false;
        }
        return result;
    }

    private void loadImageFromUri(Uri selectedImage) {
        Picasso.with(activity)
                .load(selectedImage)
                .fit()
                .into(offerThumbnail);
        offerThumbnailName.setText(extractNameFromUri(selectedImage));
        offerThumbnailCard.setVisibility(View.VISIBLE);
    }

    private CharSequence extractNameFromUri(final Uri selectedImage) {
        if ("file".equals(selectedImage.getScheme())) {
            return selectedImage.getLastPathSegment();
        } else if ("content".equals(selectedImage.getScheme())) {
            final String[] projection = {MediaStore.Images.Media.TITLE};
            final Cursor cursor = activity.getContentResolver().query(selectedImage, projection, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndexOrThrow(projection[0]));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return "";
    }
}

