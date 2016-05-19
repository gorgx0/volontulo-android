package com.stxnext.volontulo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.palaima.debugdrawer.DebugDrawer;
import io.palaima.debugdrawer.actions.ActionsModule;
import io.palaima.debugdrawer.actions.SwitchAction;
import io.palaima.debugdrawer.commons.BuildModule;
import io.palaima.debugdrawer.commons.DeviceModule;
import io.palaima.debugdrawer.commons.NetworkModule;
import io.palaima.debugdrawer.commons.SettingsModule;
import io.palaima.debugdrawer.location.LocationModule;
import io.palaima.debugdrawer.okhttp3.OkHttp3Module;
import io.palaima.debugdrawer.picasso.PicassoModule;
import io.palaima.debugdrawer.scalpel.ScalpelModule;
import io.palaima.debugdrawer.timber.TimberModule;

public abstract class VolontuloBaseActivity extends AppCompatActivity implements CollapsibleImage {

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @Nullable
    @BindView(R.id.collapsing_image)
    protected ImageView collapsingImage;

    @Nullable
    @BindView(R.id.appbar)
    protected AppBarLayout appbar;

    @Nullable
    @BindView(R.id.collapsing_toolbar)
    protected CollapsingToolbarLayout collapsingToolbar;

    @Nullable
    @BindView(R.id.fab)
    protected FloatingActionButton floatingActionButton;

    protected DebugDrawer debugDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initDebugDrawer() {
        SwitchAction  switchAction = new SwitchAction("Highlights user's actions", new SwitchAction.Listener() {
            @Override
            public void onCheckedChanged(boolean value) {
                final String preferenceFile = getString(R.string.preference_file_name);
                final String preferenceHighlightUsersOffer = getString(R.string.preference_highlight_users_offer);
                final SharedPreferences preferences = getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
                preferences.edit().putBoolean(preferenceHighlightUsersOffer, value).apply();
            }
        });

        debugDrawer = new DebugDrawer.Builder(this)
                .modules(
                        new ScalpelModule(this),
                        new TimberModule(),
                        new PicassoModule(Picasso.with(this)),
                        new ActionsModule(switchAction),
                        new OkHttp3Module(VolontuloApp.okHttpClient),
                        new BuildModule(this),
                        new NetworkModule(this),
                        new LocationModule(this),
                        new DeviceModule(this),
                        new SettingsModule(this)
                ).build();
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
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
        super.setTitle(title);
    }

    protected void init(String stringTitle) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
        }
        setTitle(stringTitle);
    }

    public final void setFABVisible(boolean isVisible) {
        if (floatingActionButton != null) {
            floatingActionButton.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        setupFloatingActionButton();
        initDebugDrawer();
    }

    private void setupFloatingActionButton() {
        if (floatingActionButton != null) {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final FragmentManager fragmentManager = getSupportFragmentManager();
                    final VolontuloBaseFragment fragment = (VolontuloBaseFragment) fragmentManager.findFragmentById(R.id.content);
                    if (fragment != null) {
                        fragment.onFabClick(floatingActionButton);
                    }
                }
            });
        }
    }

    @Override
    public void wantCollapse(@DrawableRes int imageResource) {
        boolean collapse = imageResource != 0;
        if (appbar == null || collapsingImage == null) {
            return;
        }
        if (collapse) {
            unlockAppBarOpen();
            Picasso.with(collapsingImage.getContext())
                    .load(imageResource)
                    .fit()
                    .centerCrop()
                    .into(collapsingImage);
        } else {
            lockAppBarClosed();
        }
    }

    @Override
    public void wantCollapse(String imagePath) {
        boolean collapse = !TextUtils.isEmpty(imagePath);
        if (appbar == null || collapsingImage == null) {
            return;
        }
        if (collapse) {
            unlockAppBarOpen();
            Picasso.with(collapsingImage.getContext())
                    .load(imagePath)
                    .fit()
                    .centerCrop()
                    .into(collapsingImage);
        } else {
            lockAppBarClosed();
        }
    }

    private void unlockAppBarOpen() {
        collapsingImage.setVisibility(View.VISIBLE);
        appbar.setExpanded(true);
    }

    private void lockAppBarClosed() {
        final ViewGroup.LayoutParams layoutParams = appbar.getLayoutParams();
        layoutParams.height = (int) getResources().getDimension(R.dimen.normal_height);
        appbar.setLayoutParams(layoutParams);
        collapsingToolbar.setTitleEnabled(false);
        collapsingImage.setVisibility(View.GONE);
        collapsingImage.setImageResource(0);
        appbar.setExpanded(false, false);
        appbar.setActivated(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (debugDrawer != null) {
            debugDrawer.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (debugDrawer != null) {
            debugDrawer.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (debugDrawer != null) {
            debugDrawer.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (debugDrawer != null) {
            debugDrawer.onStop();
        }
    }
}
