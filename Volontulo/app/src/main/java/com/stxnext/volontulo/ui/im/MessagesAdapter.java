package com.stxnext.volontulo.ui.im;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.logic.im.LocalMessage;
import com.stxnext.volontulo.ui.utils.BaseMockAdapter;
import com.stxnext.volontulo.ui.utils.BaseViewHolder;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.util.ArrayList;

import butterknife.Bind;
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

    public void updateStatus(LocalMessage message) {
        for (int i = objects.size() - 1; i >= 0; ++i) {
            if (message.getMessageId().equals(objects.get(i).getMessageId())) {
                objects.set(i, message);
                notifyItemChanged(i);
                break;
            }
        }
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
        public static final int DELAY_MILLIS = 1000 * 10;

        @Bind(R.id.message)
        protected TextView message;

        @Bind(R.id.message_info)
        protected ImageView messageInfo;

        public MessageHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBind(LocalMessage model) {
            final Resources resources = itemView.getResources();
            message.setText(model.getMessageTextBody());
            switch (model.getDirection()) {
                case FAILED:
                    messageInfo.setColorFilter(ResourcesCompat.getColor(resources, R.color.colorMessageFailed, null));
                    messageInfo.setVisibility(View.VISIBLE);
                    break;

                case DELIVERED:
                    final boolean isDeliveredOlder = Seconds.secondsBetween(model.getTimestamp(), new DateTime()).isGreaterThan(Seconds.seconds(10));
                    if (!isDeliveredOlder) {
                        messageInfo.setColorFilter(ResourcesCompat.getColor(resources, R.color.colorMessageDelivered, null));
                        messageInfo.setVisibility(View.VISIBLE);
                        messageInfo.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                messageInfo.setVisibility(View.INVISIBLE);
                            }
                        }, DELAY_MILLIS);
                    }
                    break;

                default:
                    messageInfo.setVisibility(View.INVISIBLE);
            }
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
