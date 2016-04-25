package com.stxnext.volontulo.ui.im;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.ui.utils.SimpleItemDivider;

import butterknife.Bind;

public class ConversationListFragment extends VolontuloBaseFragment {
    @Bind(R.id.list)
    protected RecyclerView conversationList;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_list;
    }

    @Override
    protected void onPostCreateView(View root) {
        super.onPostCreateView(root);
        requestFloatingActionButton();
        setToolbarTitle(R.string.im_conversaion_list_title);
        conversationList.setLayoutManager(new LinearLayoutManager(getActivity()));
        conversationList.setAdapter(new ConversationsAdapter(getActivity()));
        conversationList.addItemDecoration(new SimpleItemDivider(getActivity()));
        conversationList.setHasFixedSize(true);
    }

    @Override
    protected void onFabClick(FloatingActionButton button) {
        //TODO: show people selection dialog
    }
}
