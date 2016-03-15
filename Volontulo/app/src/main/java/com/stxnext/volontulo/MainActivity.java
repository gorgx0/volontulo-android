package com.stxnext.volontulo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.stxnext.volontulo.ui.offers.OfferListActivity;

import butterknife.OnClick;

public class MainActivity extends VolontuloBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init(R.string.app_name);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_action_list)
    public void gotoActionList() {
        Toast.makeText(this, "ACTION LIST", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, OfferListActivity.class));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_volunteer_list)
    public void gotoVolunteerList() {
        Toast.makeText(this, "VOLUNTEER LIST", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_communicator)
    public void gotoCommunicator() {
        Toast.makeText(this, "COMMUNICATOR", Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_settings)
    public void gotoSettings() {
        Toast.makeText(this, "SETTINGS", Toast.LENGTH_SHORT).show();
    }
}
