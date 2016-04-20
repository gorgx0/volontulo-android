package com.stxnext.volontulo.ui.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    protected T objectBinded;

    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * Binds model to view assigned with this view holder.
     * @param model Any model class that is
     */
    protected abstract void onBind(T model);

    public final void bind(T model) {
        objectBinded = model;
        onBind(model);
    }
}
