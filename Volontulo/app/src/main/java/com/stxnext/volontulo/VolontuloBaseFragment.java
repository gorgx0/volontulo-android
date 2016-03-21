package com.stxnext.volontulo;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class VolontuloBaseFragment extends Fragment {

    @LayoutRes
    protected int fragmentLayoutResource() {
        return 0;
    }

    protected void onPostCreateView(final View root) {
    }

    protected final void setTitle(final @StringRes int titleResource) {
        setTitle(getString(titleResource));
    }

    protected final void setTitle(final CharSequence title) {
        final ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(fragmentLayoutResource(), container, false);
        ButterKnife.bind(this, root);
        onPostCreateView(root);
        return root;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
