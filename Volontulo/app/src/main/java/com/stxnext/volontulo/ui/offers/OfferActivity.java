package com.stxnext.volontulo.ui.offers;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.ui.volunteers.VolunteerListFragment;

import butterknife.Bind;

public class OfferActivity extends VolontuloBaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.navigation_menu)
    protected NavigationView navigationMenu;

    @Bind(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    protected ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        init(R.string.app_name);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        navigationMenu.setNavigationItemSelectedListener(this);
        navigationMenu.setCheckedItem(R.id.menu_action_list);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
            .replace(R.id.content, new OfferListFragment())
            .commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        toggle.syncState();
        invalidateOptionsMenu();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.menu_action_list:
                fragment = new OfferListFragment();
                break;

            case R.id.menu_volunteer_list:
                fragment = new VolunteerListFragment();
                break;

            case R.id.menu_communicator:
                Toast.makeText(this, "COMMUNICATOR", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menu_settings:
                Toast.makeText(this, "SETTINGS", Toast.LENGTH_SHORT).show();
                break;

            default:
                return false;
        }
        if (fragment != null) {
            fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
            invalidateOptionsMenu();
            return true;
        }
        return false;
    }
}
