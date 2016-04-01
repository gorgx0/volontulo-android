package com.stxnext.volontulo;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
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
    protected AppBarLayout appbar;

    @Nullable
    @Bind(R.id.collapsing_toolbar)
    protected CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected final void init(int resourceTitle) {
        init(getString(resourceTitle));
    }

    @Override
    public void setTitle(int titleId) {
        setTitle(getString(titleId));
    }

    @Override
    public void setTitle(CharSequence title) {
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        if (collapsingToolbar != null) {
            collapsingToolbar.setTitle(title);
        }
    }

    protected void init(String stringTitle) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(stringTitle);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void wantCollapse(@DrawableRes int imageResource) {
        if (appbar == null || collapsingImage == null) {
            return;
        }
        boolean collapse = imageResource != 0;
        if (collapse) {
            collapsingImage.setVisibility(View.VISIBLE);
            collapsingImage.setImageResource(imageResource);
            appbar.setExpanded(true);
        } else {
            collapsingImage.setVisibility(View.GONE);
            collapsingImage.setImageResource(0);
            appbar.setExpanded(false);
            appbar.setActivated(false);
        }
    }
}
