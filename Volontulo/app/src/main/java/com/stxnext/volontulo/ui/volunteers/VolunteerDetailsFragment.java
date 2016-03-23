package com.stxnext.volontulo.ui.volunteers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;

import butterknife.Bind;

public class VolunteerDetailsFragment extends VolontuloBaseFragment {

    @Bind(R.id.text_name)
    TextView name;

    @Bind(R.id.text_email)
    TextView email;

    @Bind(R.id.text_description)
    TextView description;

    @Bind(R.id.offers)
    protected RecyclerView offers;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_volunteer_details;
    }

    @Override
    protected void onPostCreateView(View root) {
        name.setText("Fundacja Małych Wielkich Rzeczy");
        email.setText("bohater@gmail.com ");
        description.setText("Istniejemy dla i wokół Małych i wielkich spraw, wartościowych ludzi. Lubimy podejmować wyzwania i ... zło zamieniać w dobro");

        Context context = getContext();
        offers.setLayoutManager(new LinearLayoutManager(context));
        offers.setHasFixedSize(true);
        offers.setAdapter(new MockAttendsAdapter(context));
    }
}
