package com.stxnext.volontulo.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.ui.login.LoginActivity;
import com.stxnext.volontulo.ui.offers.OfferListFragment;

import butterknife.Bind;

public class MainHostActivity extends VolontuloBaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Bind(R.id.navigation_menu)
    protected NavigationView navigationMenu;

    @Bind(R.id.drawer_layout)
    protected DrawerLayout drawerLayout;

    protected ActionBarDrawerToggle toggle;

    @Bind(R.id.collapsing_image)
    protected ImageView collapsingImage;

    @Bind(R.id.appbar)
    protected View appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        init(R.string.app_name);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        navigationMenu.setNavigationItemSelectedListener(this);
        navigationMenu.setCheckedItem(R.id.menu_action_list);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        OfferListFragment fragment = new OfferListFragment();
        fragmentManager.beginTransaction()
            .replace(R.id.content, fragment)
            .commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawerLayout.closeDrawers();
        if (item.getItemId() == R.id.menu_logout) {
            final SharedPreferences preferences = getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(getString(R.string.preference_key_is_logged), false);
            editor.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return false;
        }
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final VolontuloBaseFragment fragment = NavigationDrawerFragmentFactory.create(item.getItemId());
        if (fragment != null) {
            fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .commit();

            return true;
        }
        return false;
    }

}
