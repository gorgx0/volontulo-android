package com.stxnext.volontulo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
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

    protected void onPostCreateView(final View root) {
    }

    protected final void setToolbarTitle(final @StringRes int titleResource) {
        setToolbarTitle(getString(titleResource));
    }

    protected final void setToolbarTitle(final CharSequence title) {
        VolontuloBaseActivity activity = (VolontuloBaseActivity) getActivity();
        activity.setTitle(title);
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
        super.onDestroyView();
    }

    @DrawableRes
    public int getImageResource() {
        return 0;
    }

    public String getImagePath() {
        return null;
    }
}
