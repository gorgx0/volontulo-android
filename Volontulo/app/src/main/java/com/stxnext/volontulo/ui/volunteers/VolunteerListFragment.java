package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.UserProfile;
import com.stxnext.volontulo.ui.utils.SimpleItemDivider;

import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class VolunteerListFragment extends VolontuloBaseFragment {

    private UserProfileAdapter adapter;

    @BindView(R.id.list)
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
        Timber.d("[REALM] Users count: %d", userProfileResults.size());
        adapter.swap(userProfileResults);
        Timber.d("[REALM] Users UI PUT");
        final Call<List<UserProfile>> call = VolontuloApp.api.listVolunteers();
        call.enqueue(new Callback<List<UserProfile>>() {
            @Override
            public void onResponse(Call<List<UserProfile>> call, Response<List<UserProfile>> response) {
                if (response.isSuccessful()) {
                    final List<UserProfile> userProfileList = response.body();
                    Timber.d("[RETRO] Users count: %d", userProfileList.size());
                    realm.beginTransaction();
                    realm.delete(UserProfile.class);
                    Timber.d("[REALM] Users CLEAR");
                    realm.copyToRealmOrUpdate(userProfileList);
                    Timber.d("[REALM] Users COPY/UPDATE");
                    realm.commitTransaction();
                    adapter.swap(userProfileList);
                    Timber.d("[RETRO] Users UI CLEAR");
                }
            }

            @Override
            public void onFailure(Call<List<UserProfile>> call, Throwable t) {
                String msg = "[FAILURE] message - " + t.getMessage();
                Timber.d(msg);
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
