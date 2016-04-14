package com.stxnext.volontulo.ui.volunteers;

import android.view.View;

import com.stxnext.volontulo.model.Ofer;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

public abstract class AttendViewHolder extends BaseViewHolder<Ofer> {

    public AttendViewHolder(View itemView) {
        super(itemView);
    }

//    @Bind(R.id.image)
//    ImageView image;
//
//    @Bind(R.id.title)
//    TextView title;
//
//    @Bind(R.id.location)
//    TextView location;
//
//    @Bind(R.id.start_time)
//    TextView startTime;
//
//    @Bind(R.id.end_time)
//    TextView endTime;
//
//    @Override
//    public void onBind(Ofer item) {
//        Picasso.with(image.getContext())
//                .load(item.getImageResource())
//                .fit()
//                .centerCrop()
//                .into(image);
//        title.setText(item.getName());
//        location.setText(item.getPlaceName());
//        startTime.setText(item.getFormattedStartDay());
//        endTime.setText(item.getFormattedEndDay());
//    }

}
