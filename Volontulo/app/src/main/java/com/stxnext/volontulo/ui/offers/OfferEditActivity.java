package com.stxnext.volontulo.ui.offers;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.api.Offer;

public class OfferEditActivity extends VolontuloBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodrawer);
        init(R.string.add_offer);
        if (savedInstanceState == null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final OfferEditFragment fragment = new OfferEditFragment();
            final Bundle extras = getIntent().getExtras();
            int imageResource = extras.getInt(Offer.IMAGE_RESOURCE, 0);
            int id = extras.getInt(Offer.OFFER_ID, 0);
            final Parcelable offer = extras.getParcelable(Offer.OFFER_OBJECT);
            Bundle args = new Bundle();
            args.putInt(Offer.IMAGE_RESOURCE, imageResource);
            args.putInt(Offer.OFFER_ID, id);
            if (extras.containsKey(Offer.IMAGE_PATH)) {
                args.putString(Offer.IMAGE_PATH, extras.getString(Offer.IMAGE_PATH, null));
            }
            args.putParcelable(Offer.OFFER_OBJECT, offer);
            fragment.setArguments(args);
            fragmentManager.beginTransaction()
                    .add(R.id.content, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_attach_file:
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
