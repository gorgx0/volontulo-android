package com.stxnext.volontulo;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class VolontuloBaseActivity extends AppCompatActivity implements CollapsibleImage {

    @Nullable
    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    @Nullable
    @Bind(R.id.collapsing_image)
    protected ImageView collapsingImage;

    @Nullable
    @Bind(R.id.appbar)
    protected View appbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected final void init(int resourceTitle) {
        init(getString(resourceTitle));
    }

    protected void init(String stringTitle) {
        if (toolbar != null) {
            toolbar.setTitle(stringTitle);
            setSupportActionBar(toolbar);
        }

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void wantCollapse(@DrawableRes int imageResource) {
        if (appbar == null) {
            return;
        }
        boolean collapse = imageResource != 0;
        ViewGroup.LayoutParams layoutParams = appbar.getLayoutParams();
        if (collapse) {
            collapsingImage.setVisibility(View.VISIBLE);
            collapsingImage.setImageResource(imageResource);
            layoutParams.height = getResources().getDimensionPixelSize(R.dimen.app_bar_height);
        } else {
            collapsingImage.setVisibility(View.GONE);
            collapsingImage.setImageResource(0);
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        appbar.setLayoutParams(layoutParams);
    }
}
