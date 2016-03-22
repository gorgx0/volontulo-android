package com.stxnext.volontulo.ui.volunteers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Offer;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.Bind;

public class AttendViewHolder extends BaseViewHolder<Offer> {
    public AttendViewHolder(View itemView) {
        super(itemView);
    }

    @Bind(R.id.image)
    ImageView image;

    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.location)
    TextView location;

    @Bind(R.id.time)
    TextView time;

    @Override
    public void onBind(Offer item) {
        Picasso.with(image.getContext())
                .load(item.getImageResource())
                .fit()
                .centerCrop()
                .into(image);
        title.setText(item.getName());
        location.setText(item.getPlace());
        String text = item.getFormattedStartDay() + " - " + item.getFormattedEndDay();
        time.setText(text);
    }

}
