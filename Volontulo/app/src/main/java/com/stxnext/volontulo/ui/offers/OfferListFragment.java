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
import com.stxnext.volontulo.model.Ofer;
import com.stxnext.volontulo.ui.map.MapOffersActivity;
import com.stxnext.volontulo.ui.utils.SimpleItemDivider;

import org.joda.time.DateTime;

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

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onStop() {
        super.onStop();
        realm.close();
    }

    @Override
    protected void onPostCreateView(View root) {
        offers.setLayoutManager(new LinearLayoutManager(getActivity()));
        offers.addItemDecoration(new SimpleItemDivider(getActivity()));
        offers.setHasFixedSize(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        final RealmResults<Ofer> oferResults = realm.where(Ofer.class).findAll();
        offers.setAdapter(new OffersRealmAdapter(getActivity(), combineRealmAndMocks(oferResults)));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        obtainData();
    }

    private void obtainData() {
        final Call<List<Offer>> call = VolontuloApp.api.listOffers();
        call.enqueue(new Callback<List<Offer>>() {
            @Override
            public void onResponse(Call<List<Offer>> call, Response<List<Offer>> response) {
                final int statusCode = response.code();
                final List<com.stxnext.volontulo.api.Offer> offerList = response.body();
                final String msg = "SUCCESS: status - " + statusCode;
                Log.d(TAG, msg);
                Log.d(TAG, "Ofer count: " + offerList.size());
                list = (ArrayList<Offer>) offerList;
                adapter = new OfferAdapter(getActivity(), list);
                offers.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<com.stxnext.volontulo.api.Offer>> call, Throwable t) {

            }
        });
    }

    private List<Ofer> combineRealmAndMocks(final RealmResults<Ofer> realm) {
        final List<Ofer> objects = new ArrayList<>();
        objects.addAll(realm);
        objects.add(Ofer.mock("Oferta 1", "Poznań", DateTime.now(), DateTime.now().plusDays(7), R.drawable.apple, false));
        objects.add(Ofer.mock("Oferta 2", "Polska", DateTime.now().plusMonths(3), DateTime.now().plusMonths(3).plusDays(7), R.drawable.breakfast_free, false));
        objects.add(Ofer.mock("Oferta 3", "Warszawa", DateTime.now(), DateTime.now().plusDays(7), R.drawable.cookie, true));
        objects.add(Ofer.mock("Oferta 4", "Leszno", DateTime.now().minusDays(1), DateTime.now().plusDays(7), R.drawable.ice, false));
        objects.add(Ofer.mock("Oferta 5", "Wrocław", DateTime.now().minusDays(1), DateTime.now().plusDays(7), R.drawable.join, false));
        objects.add(Ofer.mock("Oferta 6", "Poznań", DateTime.now().minusWeeks(1), DateTime.now().plusWeeks(2), R.drawable.oscar, true));
        return objects;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_offer_menu, menu);
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
