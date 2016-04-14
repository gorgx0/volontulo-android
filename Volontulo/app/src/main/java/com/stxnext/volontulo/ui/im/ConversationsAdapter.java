package com.stxnext.volontulo.ui.im;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.logic.im.Conversation;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import butterknife.Bind;
import butterknife.OnClick;

public class ConversationsAdapter extends BaseMockAdapter<Conversation, BaseViewHolder<Conversation>> {
    public ConversationsAdapter(Context context) {
        super(context);
        objects.add(new Conversation("Jan Kowalski"));
        objects.add(new Conversation("Michał Nowak"));
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
            activity.startActivity(new Intent(activity, MessagingActivity.class));
        }
    }
}
