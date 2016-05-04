package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;

import java.util.List;

class AttendsAdapter extends BaseMockAdapter<Offer, AttendViewHolder> {

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

    public AttendsAdapter(Context context) {
        super(context);
        useHeader = true;
    }

    public void swap(List<Offer> offers) {
        objects.clear();
        objects.addAll(offers);
        notifyDataSetChanged();
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
