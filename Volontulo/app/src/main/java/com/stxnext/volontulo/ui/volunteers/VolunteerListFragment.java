package com.stxnext.volontulo.ui.volunteers;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.ui.utils.SimpleItemDivider;

import butterknife.Bind;

public class VolunteerListFragment extends VolontuloBaseFragment {
    @Bind(R.id.list)
    protected RecyclerView volunteers;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    protected void onPostCreateView(View root) {
        setToolbarTitle(R.string.volunteer_list_title);
        volunteers.setLayoutManager(new LinearLayoutManager(getActivity()));
        volunteers.addItemDecoration(new SimpleItemDivider(getActivity()));
        volunteers.setHasFixedSize(true);
        volunteers.setAdapter(new MockVolunteersAdapter(getActivity()));
    }
}
