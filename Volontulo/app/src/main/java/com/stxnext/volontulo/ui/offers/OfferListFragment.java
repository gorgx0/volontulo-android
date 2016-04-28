package com.stxnext.volontulo.ui.offers;

import android.content.Context;
import android.content.Intent;
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
import com.stxnext.volontulo.ui.map.MapOffersActivity;
import com.stxnext.volontulo.ui.utils.SimpleItemDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferListFragment extends VolontuloBaseFragment {

    public static final String TAG = "RETROFIT-TEST";

    private ArrayList<Offer> list;
    private OfferAdapter adapter;

    @Bind(R.id.list)
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

//    @Override
//    public void onStart() {
//        super.onStart();
////        realm = Realm.getDefaultInstance();
//    }

//    @Override
//    public void onStop() {
//        super.onStop();
////        realm.close();
//    }

    @Override
    protected void onPostCreateView(View root) {
        offers.setLayoutManager(new LinearLayoutManager(getActivity()));
        offers.addItemDecoration(new SimpleItemDivider(getActivity()));
        offers.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "OfferList::onResume");
//        final RealmResults<Offer> offerResults = realm.where(Offer.class).findAll();
//        final OfferAdapter adapter = new OfferAdapter(getActivity(), offerResults);
//        offers.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        realm = Realm.getDefaultInstance();
        adapter = new OfferAdapter(getContext());
        offers.setAdapter(adapter);
        obtainData();
    }

    private void obtainData() {
        final RealmResults<Offer> offerResults = realm.where(Offer.class).findAll();
        if (offerResults != null) {
            adapter.update(offerResults);
        }
        final Call<List<Offer>> call = VolontuloApp.api.listOffers();
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                final List<Offer> offerList;
                String msg;
                if (response.isSuccessful()) {
                    offerList = response.body();
                    realm.beginTransaction();
                    realm.delete(Offer.class);
                    realm.copyToRealmOrUpdate(offerList);
                    realm.commitTransaction();
                    msg = "[RETRO] Offer count: " + offerList.size();

                    adapter.update(offerList);
                }
            }

            @Override
            public void onFailure(Call<List<com.stxnext.volontulo.api.Offer>> call, Throwable t) {
                String msg = "FAILURE: message - " + t.getMessage();
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
                startActivity(new Intent(getActivity(), AddOfferActivity.class));
                return true;

            case R.id.action_map_offers:
                startActivity(new Intent(getActivity(), MapOffersActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
