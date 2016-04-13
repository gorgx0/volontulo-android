package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.model.Volunteer;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;

class MockVolunteersAdapter extends BaseMockAdapter<Volunteer, VolunteerViewHolder> {

    public MockVolunteersAdapter(Context context) {
        super(context, R.layout.item_volunteer);
        objects.add(Volunteer.mock("Jan", "Nowak", R.drawable.ic_user_placeholder));
        objects.add(Volunteer.mock("Jan", "Kowalski", R.drawable.ic_user_placeholder));
        objects.add(Volunteer.mock("Jan", "Nowak", R.drawable.ic_user_placeholder));
        objects.add(Volunteer.mock("Michał", "Kowalski", R.drawable.ic_user_placeholder));
        objects.add(Volunteer.mock("Michał", "Nowak", R.drawable.ic_user_placeholder));
        objects.add(Volunteer.mock("Paweł", "Nowak", R.drawable.ic_user_placeholder));
    }

    @Override
    protected VolunteerViewHolder createViewHolder(View item, int viewType) {
        return new VolunteerViewHolder(item);
    }
}
