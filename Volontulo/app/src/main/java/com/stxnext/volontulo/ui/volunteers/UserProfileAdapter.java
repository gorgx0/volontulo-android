package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;

import java.util.List;

public class UserProfileAdapter extends BaseMockAdapter<UserProfile, UserProfileViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(View clicked, int position, UserProfile item);
    }

    private OnItemClickListener clickCallback;

    public UserProfileAdapter(Context context, List<UserProfile> results) {
        this(context, results, null);
    }

    public UserProfileAdapter(Context context, List<UserProfile> results, OnItemClickListener callback) {
        super(context, results);
        clickCallback = callback;
    }

    @Override
    protected int getLayoutResource(int viewType) {
        return R.layout.item_volunteer;
    }

    @Override
    protected UserProfileViewHolder createViewHolder(View item, int viewType) {
        return new UserProfileViewHolder(item, clickCallback);
    }

}
