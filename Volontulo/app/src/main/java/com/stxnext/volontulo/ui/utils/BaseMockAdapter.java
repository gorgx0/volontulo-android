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
    protected final List<T> objects;
    private LayoutInflater inflater;

    public BaseMockAdapter(final Context context) {
        this(context, new ArrayList<T>());
    }

    public BaseMockAdapter(final Context context, final List<T> results) {
        inflater = LayoutInflater.from(context);
        objects = results;
    }

    @Override
    public final V onCreateViewHolder(ViewGroup parent, int viewType) {
        final View inflated = inflater.inflate(getLayoutResource(viewType), parent, false);
        return createViewHolder(inflated, viewType);
    }

    @LayoutRes
    protected abstract int getLayoutResource(int viewType);

    protected abstract V createViewHolder(View item, int viewType);

    @Override
    public final void onBindViewHolder(V holder, int position) {
        holder.onBind(objects.get(position));
    }

    @Override
    public final int getItemCount() {
        return objects.size();
    }
}
