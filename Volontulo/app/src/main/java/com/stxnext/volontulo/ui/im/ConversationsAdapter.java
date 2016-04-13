package com.stxnext.volontulo.ui.im;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import butterknife.Bind;
import butterknife.OnClick;

public class ConversationsAdapter extends BaseMockAdapter<Conversation, BaseViewHolder<Conversation>> {
    public ConversationsAdapter(Context context) {
        super(context, R.layout.item_conversation);
        objects.add(new Conversation("Jan Kowalski"));
        objects.add(new Conversation("Michał Nowak"));
    }

    @Override
    protected BaseViewHolder<Conversation> createViewHolder(View item, int viewType) {
        return new ConversationHolder(item);
    }

    static class ConversationHolder extends BaseViewHolder<Conversation> {
        @Bind(R.id.participant_name)
        protected TextView participantName;

        public ConversationHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(Conversation model) {
            participantName.setText(model.getNickname());
        }

        @OnClick(R.id.conversation)
        void onConversationClick(View clicked) {
            final VolontuloBaseActivity activity = (VolontuloBaseActivity) clicked.getContext();
            final FragmentManager fragmentManager = activity.getSupportFragmentManager();
            final Fragment messagesFragment = new MessagesListFragment();
            final Bundle args = new Bundle();
            args.putString(MessagesListFragment.KEY_PARTICIPANTS, participantName.getText().toString());
            messagesFragment.setArguments(args);
            fragmentManager.beginTransaction()
                .replace(R.id.content, messagesFragment)
                .addToBackStack(null)
                .commit();
        }
    }
}
