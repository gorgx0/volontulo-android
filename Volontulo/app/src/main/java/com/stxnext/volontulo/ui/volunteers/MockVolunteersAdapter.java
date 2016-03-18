package com.stxnext.volontulo.ui.volunteers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

class MockVolunteersAdapter extends RecyclerView.Adapter<VolunteerViewHolder> {
    @Override
    public VolunteerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VolunteerViewHolder(LayoutInflater.from(parent.getContext()).inflate(0, parent, false));
    }

    @Override
    public void onBindViewHolder(VolunteerViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
