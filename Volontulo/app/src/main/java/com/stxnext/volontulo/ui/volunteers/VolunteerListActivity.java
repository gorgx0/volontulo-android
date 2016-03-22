package com.stxnext.volontulo.ui.volunteers;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;

public class VolunteerListActivity extends VolontuloBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        init(R.string.app_name);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content, new VolunteerListFragment())
                .commit();
    }
}
