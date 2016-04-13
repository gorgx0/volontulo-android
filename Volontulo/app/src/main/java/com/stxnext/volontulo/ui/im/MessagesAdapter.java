package com.stxnext.volontulo.ui.im;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.logic.im.Message;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import butterknife.Bind;

public class MessagesAdapter extends BaseMockAdapter<Message, BaseViewHolder<Message>> {
    public MessagesAdapter(Context context) {
        super(context, new int[]{R.layout.item_message_left, R.layout.item_message_right});
        objects.add(new Message("Lorem ipsum", Message.Direction.SENT));
        objects.add(new Message("Have fun!", Message.Direction.SENT));
        objects.add(new Message("Have fun!", Message.Direction.SENT));
        objects.add(new Message("Thanks!", Message.Direction.RECEIVED));
        objects.add(new Message("Have fun!", Message.Direction.SENT));
        objects.add(new Message("Thanks!", Message.Direction.RECEIVED));
        objects.add(new Message("Thanks!", Message.Direction.RECEIVED));
        objects.add(new Message("Thanks!", Message.Direction.RECEIVED));
        objects.add(new Message("Have fun!", Message.Direction.SENT));
        objects.add(new Message("Have fun!", Message.Direction.SENT));
        objects.add(new Message("Have fun!", Message.Direction.SENT));
        objects.add(new Message("Have fun!", Message.Direction.SENT));
        objects.add(new Message("Thanks!", Message.Direction.RECEIVED));
        objects.add(new Message("Thanks!", Message.Direction.RECEIVED));
        objects.add(new Message("Thanks!", Message.Direction.RECEIVED));
        objects.add(new Message("Thanks!", Message.Direction.RECEIVED));
        objects.add(new Message("Have fun!", Message.Direction.SENT));
        objects.add(new Message("Thanks!!!!!!!!", Message.Direction.RECEIVED));
    }

    @Override
    public int getItemViewType(int position) {
        return objects.get(position).getDirection().ordinal();
    }

    @Override
    protected BaseViewHolder<Message> createViewHolder(View inflatedItem, int viewType) {
        final Message.Direction direction = Message.Direction.fromInt(viewType);
        switch (direction) {
            case RECEIVED:
                return new MessageReceivedHolder(inflatedItem);

            case SENT:
            default:
                return new MessageSentHolder(inflatedItem);
        }
    }

    static abstract class MessageHolder extends BaseViewHolder<Message> {
        @Bind(R.id.message)
        protected TextView message;

        public MessageHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(Message model) {
            message.setText(model.getText());
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
