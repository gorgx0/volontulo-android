package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import butterknife.Bind;
import butterknife.OnClick;

public class UserProfileViewHolder extends BaseViewHolder<UserProfile> {

    private int id;
    private UserProfile profile;
    private UserProfileAdapter.OnItemClickListener callback;

    @Bind(R.id.volunteer_name)
    TextView volunteerName;

    @Bind(R.id.volunteer_avatar)
    ImageView volunteerAvatar;

    public UserProfileViewHolder(View itemView, UserProfileAdapter.OnItemClickListener clickCallback) {
        super(itemView);
        callback = clickCallback;
    }

    @Override
    public void onBind(UserProfile userProfile) {
        id = userProfile.getId();
        profile = userProfile;
        volunteerName.setText(userProfile.resolveName());
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
            Toast.makeText(context, "DETAILS USER PROFILE", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, VolunteerDetailsActivity.class);
            intent.putExtra(User.USER_ID, id);
            context.startActivity(intent);
        }
    }
}
