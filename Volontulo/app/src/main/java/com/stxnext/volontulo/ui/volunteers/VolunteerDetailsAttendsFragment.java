package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stxnext.volontulo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VolunteerDetailsAttendsFragment extends Fragment {

    @Bind(R.id.offers)
    protected RecyclerView offers;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer_details_attends, container, false);
        ButterKnife.bind(this, view);

        Context context = getContext();
        offers.setLayoutManager(new LinearLayoutManager(context));
        offers.setHasFixedSize(true);
        offers.setAdapter(new MockAttendsAdapter(context));

        return view;
    }
}
