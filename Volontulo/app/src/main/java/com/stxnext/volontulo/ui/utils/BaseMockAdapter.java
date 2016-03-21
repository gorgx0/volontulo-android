package com.stxnext.volontulo.ui.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseMockAdapter<T, V extends BaseViewHolder<T>> extends RecyclerView.Adapter<V> {
    protected final List<T> objects = new ArrayList<>();
    private LayoutInflater inflater;
    @LayoutRes private int layoutResource;

    public BaseMockAdapter(final Context context, @LayoutRes int layout) {
        inflater = LayoutInflater.from(context);
        layoutResource = layout;
    }

    @Override
    public final V onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflated = inflater.inflate(layoutResource, parent, false);
        return createViewHolder(inflated);
    }

    protected abstract V createViewHolder(View item);

    @Override
    public final void onBindViewHolder(V holder, int position) {
        holder.onBind(objects.get(position));
    }

    @Override
    public final int getItemCount() {
        return objects.size();
    }
}
