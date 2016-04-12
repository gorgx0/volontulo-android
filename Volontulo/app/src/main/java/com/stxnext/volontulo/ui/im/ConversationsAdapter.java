package com.stxnext.volontulo.ui.im;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import butterknife.Bind;

public class ConversationsAdapter extends BaseMockAdapter<Conversation, BaseViewHolder<Conversation>> {

    public ConversationsAdapter(Context context) {
        super(context, R.layout.item_conversation);
        objects.add(new Conversation("Jan Kowalski"));
        objects.add(new Conversation("Michał Nowak"));
    }

    @Override
    protected BaseViewHolder<Conversation> createViewHolder(View item) {
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
    }
}
