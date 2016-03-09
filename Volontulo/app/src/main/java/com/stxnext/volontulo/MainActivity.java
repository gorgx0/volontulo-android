package com.stxnext.volontulo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_action_list)
    public void gotoActionList() {
        Toast.makeText(this, "ACTION LIST", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.button_volunteer_list)
    public void gotoVolunteerList() {
        Toast.makeText(this, "VOLUNTEER LIST", Toast.LENGTH_SHORT).show();
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
