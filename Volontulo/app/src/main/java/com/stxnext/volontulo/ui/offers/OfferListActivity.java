package com.stxnext.volontulo.ui.offers;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        offers.setLayoutManager(new LinearLayoutManager(this));
        offers.setAdapter(new MockOffersAdapter());
    }
}
