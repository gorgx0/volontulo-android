package com.stxnext.volontulo.ui.volunteers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.stxnext.volontulo.model.Volunteer;

import java.util.ArrayList;
import java.util.List;

class MockVolunteersAdapter extends RecyclerView.Adapter<VolunteerViewHolder> {
    private List<Volunteer> volunteers = new ArrayList<>();

    public MockVolunteersAdapter() {
        volunteers.add(Volunteer.mock("Jan", "Nowak", 0));
        volunteers.add(Volunteer.mock("Jan", "Nowak", 0));
        volunteers.add(Volunteer.mock("Jan", "Nowak", 0));
        volunteers.add(Volunteer.mock("Jan", "Nowak", 0));
        volunteers.add(Volunteer.mock("Jan", "Nowak", 0));
        volunteers.add(Volunteer.mock("Jan", "Nowak", 0));
    }

    @Override
    public VolunteerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VolunteerViewHolder(LayoutInflater.from(parent.getContext()).inflate(0, parent, false));
    }

    @Override
    public void onBindViewHolder(VolunteerViewHolder holder, int position) {
        final Volunteer model = volunteers.get(position);
        holder.update(model);
    }

    @Override
    public int getItemCount() {
        return volunteers.size();
    }
}
