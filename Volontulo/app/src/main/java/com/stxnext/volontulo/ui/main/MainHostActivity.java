package com.stxnext.volontulo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.logic.im.ImService;
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
        if (savedInstanceState == null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            String tag = OfferListFragment.class.getSimpleName();
            OfferListFragment fragment;
            if (fragmentManager.findFragmentByTag(tag) == null) {
                fragment = new OfferListFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.content, fragment)
                        .commit();
            }
        }
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
            VolontuloApp.sessionUser.logout();
            stopService(new Intent(this, ImService.class));
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return false;
        }
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final VolontuloBaseFragment fragment = NavigationDrawerFragmentFactory.create(item.getItemId());
        if (fragment != null) {
            fragmentManager.beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
            item.setChecked(true);
            return true;
        }
        return false;
    }

}
