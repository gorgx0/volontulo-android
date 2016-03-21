package com.stxnext.volontulo.ui.volunteers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Volunteer;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

class VolunteerViewHolder extends BaseViewHolder<Volunteer> {
    @Bind(R.id.volunteer_name)
    TextView volunteerName;

    @Bind(R.id.volunteer_avatar)
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
        Toast.makeText(clicked.getContext(),
            String.format(Locale.getDefault(), "SELECTED VOLUNTEER %d", getAdapterPosition() + 1),
            Toast.LENGTH_SHORT
        ).show();
    }
}
