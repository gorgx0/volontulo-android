package com.stxnext.volontulo.ui.im;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.logic.im.Conversation;
import com.stxnext.volontulo.ui.login.LoginFragment;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.OnClick;

public class ConversationsAdapter extends BaseMockAdapter<Conversation, BaseViewHolder<Conversation>> {
    public ConversationsAdapter(Context context) {
        super(context);
        for (LoginFragment.User user : LoginFragment.MOCK_USER_TABLE) {
            objects.add(new Conversation(user));
        }
    }

    @Override
    protected int getLayoutResource(int viewType) {
        return R.layout.item_conversation;
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
            final Intent starter = new Intent(activity, MessagingActivity.class);
            starter.putExtra(MessagesListFragment.KEY_PARTICIPANTS, Parcels.wrap(objectBinded.asUser()));
            activity.startActivity(starter);
        }
    }
}
