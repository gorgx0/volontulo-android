package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.Offer;
import com.stxnext.volontulo.logic.session.SessionManager;
import com.stxnext.volontulo.ui.map.MapOffersActivity;
import com.stxnext.volontulo.ui.utils.SimpleItemDivider;

import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferListFragment extends VolontuloBaseFragment {

    public static final String TAG = "RETROFIT-TEST";
    private OfferAdapter adapter;

    @BindView(R.id.list)
    protected RecyclerView offers;

    private Realm realm;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setToolbarTitle(R.string.action_list_title);
    }

    @Override
    protected void onPostCreateView(View root) {
        offers.setAdapter(adapter);
        retrieveData();
        offers.setLayoutManager(new LinearLayoutManager(getActivity()));
        offers.addItemDecoration(new SimpleItemDivider(getActivity()));
        offers.setHasFixedSize(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        realm = Realm.getDefaultInstance();
        adapter = new OfferAdapter(getContext(), SessionManager.getInstance(getActivity()).getUserProfile().getUser().getId());
    }

    private void retrieveData() {
        final RealmResults<Offer> offerResults = realm.where(Offer.class).findAll();
        if (offerResults != null) {
            Log.d(TAG, "[REALM] Offers count: " + offerResults.size());
            adapter.swap(offerResults);
            Log.d(TAG, "[REALM] Offers UI PUT");
        }
        final Call<List<Offer>> call = VolontuloApp.api.listOffers();
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                if (response.isSuccessful()) {
                    final List<Offer> offerList = response.body();
                    realm.beginTransaction();
                    for (Offer offer : offerList) {
                        final Offer stored = realm.where(Offer.class).equalTo("id", offer.getId()).findFirst();
                        if (stored != null) {
                            offer.setLocationLatitude(stored.getLocationLatitude());
                            offer.setLocationLongitude(stored.getLocationLongitude());
                        }
                        realm.copyToRealmOrUpdate(offer);
                    }
                    Log.d(TAG, "[RETRO] Offers count: " + offerList.size());
                    Log.d(TAG, "[REALM] Offers COPY/UPDATE");
                    realm.commitTransaction();
                    adapter.swap(offerList);
                    Log.d(TAG, "[RETRO] Offers UI SWAP");
                }
            }

            @Override
            public void onFailure(Call<List<Offer>> call, Throwable t) {
                String msg = "[FAILURE] message - " + t.getMessage();
                Log.d(TAG, msg);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_offer_menu, menu);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        realm.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_offer:
                startActivity(new Intent(getActivity(), OfferSaveActivity.class));
                return true;

            case R.id.action_map_offers:
                startActivity(new Intent(getActivity(), MapOffersActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter == null || adapter.getItemCount() == 0) {
            return;
        }
        final String preferenceFile = getString(R.string.preference_file_name);
        final String offersPosition = getString(R.string.preference_offers_position);
        final String offersRefresh = getString(R.string.preference_offers_refresh);
        final SharedPreferences preferences = getContext().getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
        if (preferences.getBoolean(offersRefresh, false)) {
            int position = preferences.getInt(offersPosition, -1);
            if (position > -1) {
                final long id = adapter.getItemId(position);
                final Offer changed = realm.where(Offer.class).equalTo("id", id).findFirst();
                if (changed != null) {
                    adapter.refreshItem(position, changed);
                }
            } else {
                final Offer last = realm.where(Offer.class).findAll().last();
                adapter.add(last);
            }
        }
        preferences.edit()
                .remove(offersPosition)
                .remove(offersRefresh)
                .apply();
    }
}
