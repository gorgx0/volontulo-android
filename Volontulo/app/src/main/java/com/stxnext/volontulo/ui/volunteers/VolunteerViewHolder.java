package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Volunteer;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import butterknife.BindView;
import butterknife.OnClick;

class VolunteerViewHolder extends BaseViewHolder<Volunteer> {
    @BindView(R.id.volunteer_name)
    TextView volunteerName;

    @BindView(R.id.volunteer_avatar)
    ImageView volunteerAvatar;

    public VolunteerViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Volunteer model) {
        volunteerName.setText(String.format("%s %s", model.getName(), model.getSurname()));
        volunteerAvatar.setImageResource(model.getAvatarResouce());
    }

    @OnClick(R.id.volunteer)
    void onItemClick(View clicked) {
        Context context = clicked.getContext();
        Intent intent = new Intent(context, VolunteerDetailsActivity.class);
        context.startActivity(intent);
    }
}
