package com.stxnext.volontulo.ui.volunteers;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.api.UserProfile;

public class VolunteerDetailsActivity extends VolontuloBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodrawer);
        init(R.string.volunteer_detail_title);

        final Bundle extras = getIntent().getExtras();
        int userId = extras.getInt(User.USER_ID, 0);
        final Parcelable profile = extras.getParcelable(UserProfile.USER_PROFILE_OBJECT);
        Bundle args = new Bundle();
        args.putInt(User.USER_ID, userId);
        args.putParcelable(UserProfile.USER_PROFILE_OBJECT, profile);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final VolunteerDetailsFragment fragment = new VolunteerDetailsFragment();
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
