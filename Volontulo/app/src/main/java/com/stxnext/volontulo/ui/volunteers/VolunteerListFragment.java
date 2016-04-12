package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.ui.utils.SimpleItemDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerListFragment extends VolontuloBaseFragment {

    public static final String TAG = "RETROFIT-TEST";
    private ArrayList<UserProfile> list;
    private UserProfileAdapter adapter;

    @Bind(R.id.list)
    protected RecyclerView volunteers;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        obtainData();
    }

    private void obtainData() {
        final Call<List<UserProfile>> call = VolontuloApp.api.listVolunteers();
        call.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                final int statusCode = response.code();
                final List<UserProfile> userProfileList = response.body();
                final String msg = "SUCCESS: status - " + statusCode;
                Log.d(TAG, msg);
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                Log.d(TAG, "User count: " + userProfileList.size());
                list = (ArrayList<UserProfile>) userProfileList;
                adapter = new UserProfileAdapter(getActivity(), list);
                volunteers.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                final String msg = "FAILURE: message - " + t.getMessage();
                Log.d(TAG, msg);
                volunteers.setAdapter(new MockVolunteersAdapter(getActivity()));
            }
        });
    }

    @Override
    protected void onPostCreateView(View root) {
        setToolbarTitle(R.string.volunteer_list_title);
        volunteers.setLayoutManager(new LinearLayoutManager(getActivity()));
        volunteers.addItemDecoration(new SimpleItemDivider(getActivity()));
        volunteers.setHasFixedSize(true);
    }
}
