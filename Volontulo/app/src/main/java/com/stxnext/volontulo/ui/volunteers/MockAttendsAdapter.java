package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.model.Ofer;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;

import org.joda.time.DateTime;

class MockAttendsAdapter extends BaseMockAdapter<Ofer, AttendViewHolder> {

    public static final int VIEW_TYPE_HEADER = 0;
    public static final int VIEW_TYPE_DATA = 1;

    private UserProfile userProfile;

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_DATA;
    }

    public MockAttendsAdapter(Context context) {
        super(context);
        objects.add(Ofer.mock("Mali bohaterowie wśród nas", "Wielkopolska", DateTime.now(), DateTime.now().plusDays(7), R.drawable.apple, false));
        objects.add(Ofer.mock("Zbiórka materiałów szkolnych", "Polska", DateTime.now().plusMonths(3), DateTime.now().plusMonths(3).plusDays(7), R.drawable.breakfast_free, false));
        objects.add(Ofer.mock("Nowy kosz dla Oscara", "Ulica Sezamkowa", DateTime.now(), DateTime.now().plusDays(7), R.drawable.cookie, true));
        objects.add(Ofer.mock("Uszczęśliw seniora :)", "Poznań", DateTime.now().minusDays(1), DateTime.now().plusDays(7), R.drawable.ice, false));
        objects.add(Ofer.mock("SŁOŃCE DLA WSZYSTKICH", "POZNAŃ - MALTA", DateTime.now().minusDays(1), DateTime.now().plusDays(7), R.drawable.join, false));
    }

    @Override
    protected AttendViewHolder createViewHolder(View item, int viewType) {
        if (viewType == VIEW_TYPE_DATA) {
            return new AttendDataViewHolder(item);
        } else {
            final AttendHeaderViewHolder attendHeaderViewHolder = new AttendHeaderViewHolder(item);
            attendHeaderViewHolder.setUserProfile(userProfile);
            return attendHeaderViewHolder;
        }
    }

    @Override
    protected int getLayoutResource(int viewType) {
        return viewType == VIEW_TYPE_HEADER ? R.layout.item_attend_header : R.layout.item_attend_data;
    }
}
