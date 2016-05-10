package com.stxnext.volontulo.ui.im;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.logic.im.LocalMessage;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmResults;

public class MessagesAdapter extends BaseMockAdapter<LocalMessage, BaseViewHolder<LocalMessage>> {

    public MessagesAdapter(Context context) {
        super(context, new ArrayList<LocalMessage>());
    }

    public void updateData(RealmResults<LocalMessage> messages) {
        if (objects.isEmpty()) {
            objects.addAll(messages);
        } else {
            objects.retainAll(messages);
        }
        notifyDataSetChanged();
    }

    public void updateSingle(LocalMessage message) {
        objects.add(message);
        notifyItemRangeChanged(objects.size(), 1);
    }

    @Override
    protected int getLayoutResource(int viewType) {
        final LocalMessage.Direction direction = LocalMessage.Direction.fromInt(viewType);
        switch (direction) {
            case RECEIVED:
                return R.layout.item_message_left;

            case SENT:
            default:
                return R.layout.item_message_right;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return objects.get(position).getDirection().ordinal();
    }

    @Override
    protected BaseViewHolder<LocalMessage> createViewHolder(View inflatedItem, int viewType) {
        final LocalMessage.Direction direction = LocalMessage.Direction.fromInt(viewType);
        switch (direction) {
            case RECEIVED:
                return new MessageReceivedHolder(inflatedItem);

            case SENT:
            default:
                return new MessageSentHolder(inflatedItem);
        }
    }

    static abstract class MessageHolder extends BaseViewHolder<LocalMessage> {
        @BindView(R.id.message)
        protected TextView message;

        public MessageHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(LocalMessage model) {
            message.setText(model.getMessageTextBody());
            if (model.getState() == LocalMessage.State.UNREAD) {
                final Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                model.read();
                realm.copyToRealmOrUpdate(model);
                realm.commitTransaction();
                realm.close();
            }
        }
    }

    static class MessageReceivedHolder extends MessageHolder {
        public MessageReceivedHolder(View itemView) {
            super(itemView);
        }
    }

    static class MessageSentHolder extends MessageHolder {
        public MessageSentHolder(View itemView) {
            super(itemView);
        }
    }
}
