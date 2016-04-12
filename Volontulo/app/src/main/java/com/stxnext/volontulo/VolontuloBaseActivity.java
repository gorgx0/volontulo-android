package com.stxnext.volontulo;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
            Picasso.with(collapsingImage.getContext())
                    .load(imageResource)
                    .fit()
                    .centerCrop()
                    .into(collapsingImage);
            collapsingImage.setImageResource(imageResource);
            appbar.setExpanded(true);
        } else {
            collapsingImage.setVisibility(View.GONE);
            collapsingImage.setImageResource(0);
            appbar.setExpanded(false);
            appbar.setActivated(false);
        }
    }

    @Override
    public void wantCollapse(String imagePath) {
        if (appbar == null || collapsingImage == null) {
            return;
        }
        boolean collapse = !TextUtils.isEmpty(imagePath);
        if (collapse) {
            collapsingImage.setVisibility(View.VISIBLE);
            Picasso.with(collapsingImage.getContext())
                    .load(imagePath)
                    .fit()
                    .centerCrop()
                    .into(collapsingImage);
            appbar.setExpanded(true);
        } else {
            collapsingImage.setVisibility(View.GONE);
            collapsingImage.setImageResource(0);
            appbar.setExpanded(false);
            appbar.setActivated(false);
        }
    }
}
