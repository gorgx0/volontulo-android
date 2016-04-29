package com.stxnext.volontulo.ui.volunteers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.api.UserProfile;

import butterknife.Bind;

public class AttendHeaderViewHolder extends AttendViewHolder {

    @Bind(R.id.text_name)
    TextView name;

    @Bind(R.id.text_email)
    TextView email;

    @Bind(R.id.text_description)
    TextView description;

    @Bind(R.id.text_phone)
    TextView phone;

    @Bind(R.id.image)
    ImageView image;

    private UserProfile userProfile;

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public AttendHeaderViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Offer model) {
        if (userProfile != null) {
            name.setText(userProfile.resolveName());
            email.setText(userProfile.getEmail());
            phone.setText(userProfile.getPhoneNo());
            Picasso.with(image.getContext())
                    .load(userProfile.getImage())
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.ic_user_placeholder)
                    .into(image);
        }
    }
}
