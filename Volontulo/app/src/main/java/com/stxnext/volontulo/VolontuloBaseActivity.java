package com.stxnext.volontulo;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.ButterKnife;

public abstract class VolontuloBaseActivity extends AppCompatActivity {

    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    protected void init(int resourceTitle) {
        init();
        toolbar.setTitle(resourceTitle);
    }

    protected void init(String stringTitle) {
        init();
        toolbar.setTitle(stringTitle);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}
