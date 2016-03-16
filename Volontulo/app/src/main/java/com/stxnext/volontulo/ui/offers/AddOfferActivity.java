package com.stxnext.volontulo.ui.offers;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class AddOfferActivity extends VolontuloBaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int REQUEST_IMAGE = 0x1011;
    private static final int LOADER_CONTENT_IMAGE = 0x1000;
    private static final String[] filePathColumn = { MediaStore.Images.Media.DATA };

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_add);
        init(R.string.add_offer);
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
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), REQUEST_IMAGE);
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
            final Bundle args = new Bundle();
            args.putString("uri", selectedImage.toString());
            getSupportLoaderManager().initLoader(LOADER_CONTENT_IMAGE, args, this);
        }
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case LOADER_CONTENT_IMAGE:
                final Uri imageDataUri = Uri.parse(args.getString("uri"));
                return new CursorLoader(this, imageDataUri, filePathColumn, null, null, null);

            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        final int columnIndex = data.getColumnIndex(filePathColumn[0]);
        final String imageFilePath = data.getString(columnIndex);
        Log.i("Loader", String.format("Attachment path: %s", imageFilePath));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
