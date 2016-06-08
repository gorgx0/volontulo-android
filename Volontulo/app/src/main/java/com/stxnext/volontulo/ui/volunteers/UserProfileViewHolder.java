package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.OnClick;

public class UserProfileViewHolder extends BaseViewHolder<UserProfile> {

    private int id;
    private UserProfile profile;
    private UserProfileAdapter.OnItemClickListener callback;

    @BindView(R.id.volunteer_name)
    TextView volunteerName;

    @BindView(R.id.volunteer_avatar)
    ImageView volunteerAvatar;

    public UserProfileViewHolder(View itemView, UserProfileAdapter.OnItemClickListener clickCallback) {
        super(itemView);
        callback = clickCallback;
    }

    @Override
    public void onBind(UserProfile userProfile) {
        id = userProfile.getId();
        profile = userProfile;
        volunteerName.setText("[" + userProfile.getId() + "/" + userProfile.getUser().getId() + "] " + userProfile.retrieveName());
        Picasso.with(volunteerAvatar.getContext())
                .load(userProfile.getImage())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_user_placeholder)
                .into(volunteerAvatar);
    }

    @OnClick(R.id.volunteer)
    void onItemClick(View clicked) {
        if (callback != null) {
            callback.onItemClick(clicked, getAdapterPosition(), profile);
        } else {
            Context context = clicked.getContext();
            Intent intent = new Intent(context, VolunteerDetailsActivity.class);
            intent.putExtra(UserProfile.USER_PROFILE_ID, id);
            intent.putExtra(UserProfile.USER_PROFILE_OBJECT, Parcels.wrap(profile));

            context.startActivity(intent);
        }
    }
}
