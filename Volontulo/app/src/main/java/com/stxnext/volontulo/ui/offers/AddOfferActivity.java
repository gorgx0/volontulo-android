package com.stxnext.volontulo.ui.offers;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.model.Offer;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.OnClick;

public class AddOfferActivity extends VolontuloBaseActivity {
    private static final int REQUEST_IMAGE = 0x1011;
    private static final String KEY_OFFER_FORM = "offer-form";

    @Bind(R.id.offer_name_layout)
    TextInputLayout offerNameLayout;
    @Bind(R.id.offer_name)
    EditText offerName;
    @Bind(R.id.offer_place_layout)
    TextInputLayout offerPlaceLayout;
    @Bind(R.id.offer_place)
    EditText offerPlace;
    @Bind(R.id.offer_description_layout)
    TextInputLayout offerDescriptionLayout;
    @Bind(R.id.offer_description)
    EditText offerDescription;
    @Bind(R.id.offer_time_requirement_layout)
    TextInputLayout offerTimeRequirementLayout;
    @Bind(R.id.offer_time_requirement)
    EditText offerTimeRequirement;
    @Bind(R.id.offer_benefits_layout)
    TextInputLayout offerBenefitsLayout;
    @Bind(R.id.offer_benefits)
    EditText offerBenefits;
    @Bind(R.id.offer_thumbnail_card)
    View offerThumbnailCard;
    @Bind(R.id.offer_thumbnail)
    ImageView offerThumbnail;
    @Bind(R.id.offer_thumbnail_name)
    TextView offerThumbnailName;
    @Bind(R.id.scroller)
    ScrollView scrollView;

    private Offer formState = new Offer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_add);
        init(R.string.add_offer);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_OFFER_FORM, Parcels.wrap(formState));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        formState = Parcels.unwrap(savedInstanceState.getParcelable(KEY_OFFER_FORM));
        fillFormFrom(formState);
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

    @Override
    protected void onResume() {
        super.onResume();
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.attach_image_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            final Uri selectedImage = data.getData();
            loadImageFromUri(selectedImage);
        }
    }

    private void loadImageFromUri(Uri selectedImage) {
        Picasso.with(this)
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
            final Cursor cursor = getContentResolver().query(selectedImage, projection, null, null, null);
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

    @OnClick(R.id.add_offer)
    void onOfferAdd(View clicked) {
        if (!validateFields()) {
            return;
        }
        finish();
    }

    private boolean validateFields() {
        boolean result = true;
        if (TextUtils.isEmpty(offerName.getText())) {
            offerNameLayout.setError("Określ, jak oferta będzie nazywać się w systemie, staraj się być dokładnym.");
            result = false;
        }
        if (TextUtils.isEmpty(offerPlace.getText())) {
            offerPlaceLayout.setError("Określ, w jakim miejscu odbywać się będzie wolontariat.");
            result = false;
        }
        if (TextUtils.isEmpty(offerDescription.getText())) {
            offerDescriptionLayout.setError("Postaraj się dokładnie opisać akcję.");
            result = false;
        }
        if (TextUtils.isEmpty(offerTimeRequirement.getText())) {
            offerTimeRequirementLayout.setError("Określ wymagania czasowe - ile godzin pracy w ciągu dnia wymaga dana akcja.");
            result = false;
        }
        if (TextUtils.isEmpty(offerBenefits.getText())) {
            offerBenefitsLayout.setError("Określ korzyści płynące z udziału w akcji.");
            result = false;
        }
        return result;
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
}
