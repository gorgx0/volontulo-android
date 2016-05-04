package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.logic.im.Conversation;
import com.stxnext.volontulo.model.Ofer;
import com.stxnext.volontulo.ui.im.MessagesListFragment;
import com.stxnext.volontulo.ui.im.MessagingActivity;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.OnClick;

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

    @OnClick(R.id.send_message_to)
    void onClick(View clicked) {
        final Context context = clicked.getContext();
        final User user = userProfile.getUser();
        final Conversation conversation = Conversation.createOrUpdate(context, user);
        final Intent start = new Intent(context, MessagingActivity.class);
        start.putExtra(MessagesListFragment.KEY_PARTICIPANTS, Parcels.wrap(conversation));
        context.startActivity(start);
    }
}
