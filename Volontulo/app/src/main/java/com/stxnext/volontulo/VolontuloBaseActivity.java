package com.stxnext.volontulo;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class VolontuloBaseActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void init(int resourceTitle) {
        toolbar.setTitle(resourceTitle);
    }

    protected void init(String stringTitle) {
        toolbar.setTitle(stringTitle);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}
