package com.stxnext.volontulo.ui.main;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.ui.offers.OfferListFragment;
import com.stxnext.volontulo.ui.volunteers.VolunteerListFragment;

final class FragmentFactory {
    public static Fragment create(@IdRes int itemId) {
        switch (itemId) {
            case R.id.menu_action_list:
                return new OfferListFragment();

            case R.id.menu_volunteer_list:
                return new VolunteerListFragment();

            case R.id.menu_communicator:
            case R.id.menu_settings:
            default:
                return null;
        }
    }
}
