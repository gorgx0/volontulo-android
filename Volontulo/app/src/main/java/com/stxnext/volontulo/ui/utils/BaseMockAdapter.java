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
    public static final int NO_POSITION_SELECTED = -1;
    protected final List<T> objects;
    private LayoutInflater inflater;
    private int selectedPosition = NO_POSITION_SELECTED;

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

    protected abstract V createViewHolder(View inflatedItem, int viewType);

    @Override
    public final void onBindViewHolder(V holder, int position) {
        holder.bind(objects.get(position));
        holder.onItemSelected(selectedPosition == position);
    }

    @Override
    public final int getItemCount() {
        return objects.size();
    }

    public void setSelected(int position) {
        if (selectedPosition != NO_POSITION_SELECTED) {
            int oldPosition = selectedPosition;
            selectedPosition = NO_POSITION_SELECTED;
            notifyItemChanged(oldPosition);
        }
        selectedPosition = position;
        notifyItemChanged(position);
    }
}
