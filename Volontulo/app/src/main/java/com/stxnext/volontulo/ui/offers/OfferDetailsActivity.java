package com.stxnext.volontulo.ui.offers;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.model.Offer;

public class OfferDetailsActivity extends VolontuloBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodrawer);
        init(R.string.title_activity_offers_details);

        final Bundle extras = getIntent().getExtras();
        int imageResource = extras.getInt("IMAGE-RESOURCE", 0);
        int id = extras.getInt("OFFER-ID", 0);
        Bundle args = new Bundle();
        args.putInt(Offer.IMAGE_RESOURCE, imageResource);
        args.putInt("OFFER-ID", id);
        if (extras.containsKey("IMAGE-PATH")) {
            args.putString("IMAGE-PATH", extras.getString("IMAGE-PATH", null));
        }

        final FragmentManager fragmentManager = getSupportFragmentManager();
        OfferDetailsFragment fragment = new OfferDetailsFragment();
        fragment.setArguments(args);
        fragmentManager.beginTransaction()
            .replace(R.id.content, fragment)
            .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
