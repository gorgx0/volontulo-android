package com.stxnext.volontulo.ui.volunteers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stxnext.volontulo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VolunteerDetailsDataFragment extends Fragment {

    @Bind(R.id.text_name)
    TextView name;

    @Bind(R.id.text_email)
    TextView email;

    @Bind(R.id.text_description)
    TextView description;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_volunteer_details_data, container, false);
        ButterKnife.bind(this, view);
        name.setText("Fundacja Małych Wielkich Rzeczy");
        email.setText("bohater@gmail.com ");
        description.setText("Istniejemy dla i wokół Małych i wielkich spraw, wartościowych ludzi. Lubimy podejmować wyzwania i ... zło zamieniać w dobro");
        return view;
    }
}
