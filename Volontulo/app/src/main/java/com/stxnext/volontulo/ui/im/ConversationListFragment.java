package com.stxnext.volontulo.ui.im;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.ui.utils.SimpleItemDivider;

import butterknife.Bind;

public class ConversationListFragment extends VolontuloBaseFragment {
    private static final int REQUEST_CHOOSE_VOLUNTEER = 1000;

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
        final RecipientChooserDialog chooserDialog = new RecipientChooserDialog();
        chooserDialog.setTargetFragment(this, REQUEST_CHOOSE_VOLUNTEER);
        chooserDialog.show(getFragmentManager(), RecipientChooserDialog.EXTRA_KEY_USER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHOOSE_VOLUNTEER && resultCode == Activity.RESULT_OK) {
            //TODO grab data from intent and create conversation and switch to new conversation or update current conversation
            Toast.makeText(getActivity(), String.format("Grabbed: %s", data.getStringExtra("user")), Toast.LENGTH_LONG).show();
        }
    }
}
