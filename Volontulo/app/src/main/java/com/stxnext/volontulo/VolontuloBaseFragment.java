package com.stxnext.volontulo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

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
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
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
        collapsibleImage.wantCollapse(getImageResource());
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
}
