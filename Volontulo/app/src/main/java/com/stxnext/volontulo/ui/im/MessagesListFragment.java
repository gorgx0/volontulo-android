package com.stxnext.volontulo.ui.im;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;

import butterknife.Bind;

public class MessagesListFragment extends VolontuloBaseFragment {
    public static final String KEY_PARTICIPANTS = "participants";

    @Bind(R.id.list)
    protected RecyclerView messagesList;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    protected void onPostCreateView(View root) {
        super.onPostCreateView(root);
        final Bundle args = getArguments();
        setToolbarTitle(String.format("Rozmowa z %s", args.getString(KEY_PARTICIPANTS)));
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        messagesList.setLayoutManager(layoutManager);
        messagesList.setAdapter(new MessagesAdapter(getActivity()));
    }
}
