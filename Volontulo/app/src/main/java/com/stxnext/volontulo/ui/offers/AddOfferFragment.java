package com.stxnext.volontulo.ui.offers;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
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

import com.squareup.picasso.Picasso;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.model.Offer;
import com.stxnext.volontulo.ui.utils.BaseTextWatcher;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.OnClick;

public class AddOfferFragment extends VolontuloBaseFragment {
    private static final int REQUEST_IMAGE = 0x1011;
    private static final String KEY_OFFER_FORM = "offer-form";

    @Bind(R.id.offer_name_layout) TextInputLayout offerNameLayout;
    @Bind(R.id.offer_name) EditText offerName;
    @Bind(R.id.offer_place_layout) TextInputLayout offerPlaceLayout;
    @Bind(R.id.offer_place) EditText offerPlace;
    @Bind(R.id.offer_description_layout) TextInputLayout offerDescriptionLayout;
    @Bind(R.id.offer_description) EditText offerDescription;
    @Bind(R.id.offer_time_requirement_layout) TextInputLayout offerTimeRequirementLayout;
    @Bind(R.id.offer_time_requirement) EditText offerTimeRequirement;
    @Bind(R.id.offer_benefits_layout) TextInputLayout offerBenefitsLayout;
    @Bind(R.id.offer_benefits) EditText offerBenefits;
    @Bind(R.id.offer_thumbnail_card) View offerThumbnailCard;
    @Bind(R.id.offer_thumbnail) ImageView offerThumbnail;
    @Bind(R.id.offer_thumbnail_name) TextView offerThumbnailName;
    @Bind(R.id.scroller) ScrollView scrollView;

    private Offer formState = new Offer();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_offer_add;
    }

    @Override
    protected void onPostCreateView(View root) {
        setHasOptionsMenu(true);
        offerName.addTextChangedListener(new OfferObjectUpdater(offerName.getId(), formState));
        offerPlace.addTextChangedListener(new OfferObjectUpdater(offerPlace.getId(), formState));
        offerDescription.addTextChangedListener(new OfferObjectUpdater(offerDescription.getId(), formState));
        offerBenefits.addTextChangedListener(new OfferObjectUpdater(offerBenefits.getId(), formState));
        offerTimeRequirement.addTextChangedListener(new OfferObjectUpdater(offerTimeRequirement.getId(), formState));
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
            formState.setImagePath(selectedImage);
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
    public void onResume() {
        super.onResume();
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
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
            formState = Parcels.unwrap(savedInstanceState.getParcelable(KEY_OFFER_FORM));
            fillFormFrom(formState);
        }
    }

    private void fillFormFrom(Offer formState) {
        offerName.setText(formState.getName());
        offerPlace.setText(formState.getPlace());
        offerDescription.setText(formState.getDescription());
        offerTimeRequirement.setText(formState.getTimeRequirement());
        offerBenefits.setText(formState.getBenefits());
        if (!TextUtils.isEmpty(formState.getImagePath())) {
            loadImageFromUri(Uri.parse(formState.getImagePath()));
        } else {
            unloadImage();
        }
    }

    @OnClick(R.id.offer_thumbnail_delete)
    void onThumbnailDelete(View clicked) {
        unloadImage();
    }

    private void unloadImage() {
        offerThumbnail.setImageDrawable(null);
        offerThumbnailName.setText("");
        offerThumbnailCard.setVisibility(View.GONE);
    }

    @OnClick(R.id.add_offer)
    void onOfferAdd(View clicked) {
        if (!validateFields()) {
            return;
        }
        getActivity().finish();
    }

    private boolean validateFields() {
        boolean result = true;
        if (TextUtils.isEmpty(offerName.getText())) {
            offerNameLayout.setError(getString(R.string.offer_name_validate));
            result = false;
        }
        if (TextUtils.isEmpty(offerPlace.getText())) {
            offerPlaceLayout.setError(getString(R.string.offer_place_validate));
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
        Picasso.with(getActivity())
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
            final Cursor cursor = getActivity().getContentResolver().query(selectedImage, projection, null, null, null);
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
                    updated.setName(result);
                    break;

                case R.id.offer_place:
                    updated.setPlace(result);
                    break;

                case R.id.offer_description:
                    updated.setDescription(result);
                    break;

                case R.id.offer_benefits:
                    updated.setBenefits(result);
                    break;

                case R.id.offer_time_requirement:
                    updated.setTimeRequirement(result);
                    break;
            }
        }
    }
}
