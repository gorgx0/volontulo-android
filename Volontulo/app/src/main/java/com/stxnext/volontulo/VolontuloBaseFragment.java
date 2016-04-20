package com.stxnext.volontulo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class VolontuloBaseFragment extends Fragment {

    @LayoutRes
    protected abstract int getLayoutResource();

    protected CollapsibleImage collapsibleImage;
    private static final String FAB_STATE = "fab-requested-key";
    private boolean hasFloatingActionButtonRequested = false;

    protected void onPostCreateView(final View root) {
    }

    protected final void setToolbarTitle(final @StringRes int titleResource) {
        setToolbarTitle(getString(titleResource));
    }

    protected final void setToolbarTitle(final CharSequence title) {
        VolontuloBaseActivity activity = (VolontuloBaseActivity) getActivity();
        activity.setTitle(title);
    }

    protected final void requestFloatingActionButton() {
        hasFloatingActionButtonRequested = true;
        VolontuloBaseActivity activity = (VolontuloBaseActivity) getActivity();
        activity.setFABVisible(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(FAB_STATE, hasFloatingActionButtonRequested);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            hasFloatingActionButtonRequested = savedInstanceState.getBoolean(FAB_STATE, false);
            VolontuloBaseActivity activity = (VolontuloBaseActivity) getActivity();
            activity.setFABVisible(hasFloatingActionButtonRequested);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        collapsibleImage = (CollapsibleImage) getActivity();
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, root);
        if (TextUtils.isEmpty(getImagePath())) {
            collapsibleImage.wantCollapse(getImageResource());
        } else {
            collapsibleImage.wantCollapse(getImagePath());
        }
        onPostCreateView(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        VolontuloBaseActivity activity = (VolontuloBaseActivity) getActivity();
        activity.setFABVisible(false);
        super.onDestroyView();
    }

    @DrawableRes
    public int getImageResource() {
        return 0;
    }

    public String getImagePath() {
        return null;
    }

    protected void onFabClick(FloatingActionButton button) {
    }
}
