package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.ui.utils.SimpleItemDivider;

import java.util.List;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerListFragment extends VolontuloBaseFragment {

    public static final String TAG = "RETROFIT-TEST";
    private UserProfileAdapter adapter;

    @Bind(R.id.list)
    protected RecyclerView volunteers;
    private Realm realm;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        realm = Realm.getDefaultInstance();
        adapter = new UserProfileAdapter(getContext());
    }


    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    private void retrieveData() {
        final RealmResults<UserProfile> userProfileResults = realm.where(UserProfile.class).findAll();
        if (userProfileResults != null) {
            Log.d(TAG, "[REALM] Users count: " + userProfileResults.size());
            adapter.swap(userProfileResults);
            Log.d(TAG, "[REALM] Users UI PUT");
        }
        final Call<List<UserProfile>> call = VolontuloApp.api.listVolunteers();
        call.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                if (response.isSuccessful()) {
                    final List<UserProfile> userProfileList = response.body();
                    Log.d(TAG, "[RETRO] Users count: " + userProfileList.size());
                    realm.beginTransaction();
                    realm.delete(UserProfile.class);
                    Log.d(TAG, "[REALM] Users CLEAR");
                    realm.copyToRealmOrUpdate(userProfileList);
                    Log.d(TAG, "[REALM] Users COPY/UPDATE");
                    realm.commitTransaction();
                    adapter.swap(userProfileList);
                    Log.d(TAG, "[RETRO] Users UI CLEAR");
                }
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                String msg = "[FAILURE] message - " + t.getMessage();
                Log.d(TAG, msg);
            }
        });
    }

    @Override
    protected void onPostCreateView(View root) {
        setToolbarTitle(R.string.volunteer_list_title);
        volunteers.setAdapter(adapter);
        retrieveData();
        volunteers.setLayoutManager(new LinearLayoutManager(getActivity()));
        volunteers.addItemDecoration(new SimpleItemDivider(getActivity()));
        volunteers.setHasFixedSize(true);
    }

}
