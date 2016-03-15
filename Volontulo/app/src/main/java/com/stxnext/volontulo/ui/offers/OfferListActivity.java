package com.stxnext.volontulo.ui.offers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;

import butterknife.Bind;

public class OfferListActivity extends VolontuloBaseActivity {
    @Bind(R.id.offers)
    protected RecyclerView offers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_list);
        init(R.string.action_list_title);

        offers.setLayoutManager(new LinearLayoutManager(this));
        offers.setAdapter(new MockOffersAdapter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_offer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_offer:
                startActivity(new Intent(this, AddOfferActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
