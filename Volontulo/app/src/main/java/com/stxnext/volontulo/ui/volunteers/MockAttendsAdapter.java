package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Ofer;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;

import org.joda.time.DateTime;

class MockAttendsAdapter extends BaseMockAdapter<Ofer, AttendViewHolder> {

    public MockAttendsAdapter(Context context) {
        super(context, R.layout.item_attend);
        objects.add(Ofer.mock("Mali bohaterowie wśród nas", "Wielkopolska", DateTime.now(), DateTime.now().plusDays(7), R.drawable.apple, false));
        objects.add(Ofer.mock("Zbiórka materiałów szkolnych", "Polska", DateTime.now().plusMonths(3), DateTime.now().plusMonths(3).plusDays(7), R.drawable.breakfast_free, false));
        objects.add(Ofer.mock("Nowy kosz dla Oscara", "Ulica Sezamkowa", DateTime.now(), DateTime.now().plusDays(7), R.drawable.cookie, true));
        objects.add(Ofer.mock("Uszczęśliw seniora :)", "Poznań", DateTime.now().minusDays(1), DateTime.now().plusDays(7), R.drawable.ice, false));
        objects.add(Ofer.mock("SŁOŃCE DLA WSZYSTKICH", "POZNAŃ - MALTA", DateTime.now().minusDays(1), DateTime.now().plusDays(7), R.drawable.join, false));
    }

    @Override
    protected AttendViewHolder createViewHolder(View item) {
        return new AttendViewHolder(item);
    }
}
