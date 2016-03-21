package com.stxnext.volontulo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.stxnext.volontulo.ui.offers.OfferActivity;
import com.stxnext.volontulo.ui.volunteers.VolunteerListActivity;

import butterknife.OnClick;

public class MainActivity extends VolontuloBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(R.string.app_name);
    }

    @OnClick(R.id.button_action_list)
    public void gotoActionList() {
        startActivity(new Intent(this, OfferActivity.class));
    }

    @OnClick(R.id.button_volunteer_list)
    public void gotoVolunteerList() {
        startActivity(new Intent(this, VolunteerListActivity.class));
    }

    @OnClick(R.id.button_communicator)
    public void gotoCommunicator() {
        Toast.makeText(this, "COMMUNICATOR", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_settings)
    public void gotoSettings() {
        Toast.makeText(this, "SETTINGS", Toast.LENGTH_SHORT).show();
    }
}
