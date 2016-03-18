package com.stxnext.volontulo.ui.volunteers;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;

import butterknife.Bind;

public class VolunteerListActivity extends VolontuloBaseActivity {
    @Bind(R.id.offers)
    protected RecyclerView volunteers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_list);
        init(R.string.volunteer_list_title);

        volunteers.setLayoutManager(new LinearLayoutManager(this));
        volunteers.setHasFixedSize(true);
        volunteers.setAdapter(new MockVolunteersAdapter());
    }
}
