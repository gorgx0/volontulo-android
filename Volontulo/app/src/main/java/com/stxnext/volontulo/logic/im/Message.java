package com.stxnext.volontulo.logic.im;

import android.text.TextUtils;

import com.stxnext.volontulo.utils.realm.RealmString;
import com.stxnext.volontulo.utils.realm.RealmTuple;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Message extends RealmObject {
    public enum Direction {
        RECEIVED,
        SENT,
        UKNOWN;
        private static Direction[] cachedValues = null;

        public static Direction fromInt(int value) {
            if (cachedValues == null) {
                cachedValues = values();
            }
            return cachedValues[value];
        }
    }

    public enum State {
        UNREAD,
        READ
    }

    @PrimaryKey
    private String messageId;
    private RealmList<RealmTuple> messageHeaders;
    private String messageTextBody;
    private String senderId;
    private RealmList<RealmString> recipientIds;
    private Date timestamp;
    private String directionString;
    private String stateString;

    public Message() {
    }

    public static Message createFrom(com.sinch.android.rtc.messaging.Message message) {
        final Message result = new Message();
        result.messageId = message.getMessageId();
        result.messageHeaders = listFromMap(message.getHeaders());
        result.messageTextBody = message.getTextBody();
        result.senderId = message.getSenderId();
        result.recipientIds = createRecipients(message.getRecipientIds());
        result.timestamp = message.getTimestamp();
        result.directionString = Direction.RECEIVED.toString();
        result.stateString = State.UNREAD.toString();
        return result;
    }

    private static RealmList<RealmString> createRecipients(List<String> recipientIds) {
        final RealmList<RealmString> listRecipients = new RealmList<>();
        for (final String recipient : recipientIds) {
            listRecipients.add(new RealmString(recipient));
        }
        return listRecipients;
    }

    private static RealmList<RealmTuple> listFromMap(Map<String, String> headers) {
        final RealmList<RealmTuple> listHeaders = new RealmList<>();
        for (final Map.Entry<String, String> entry : headers.entrySet()) {
            listHeaders.add(new RealmTuple(entry.getKey(), entry.getValue()));
        }
        return listHeaders;
    }

    public String getMessageId() {
        return messageId;
    }

    public RealmList<RealmTuple> getMessageHeaders() {
        return messageHeaders;
    }

    public String getMessageTextBody() {
        return messageTextBody;
    }

    public String getSenderId() {
        return senderId;
    }

    public RealmList<RealmString> getRecipientIds() {
        return recipientIds;
    }

    public DateTime getTimestamp() {
        return new DateTime(timestamp);
    }

    public Direction getDirection() {
        return TextUtils.isEmpty(directionString) ? Direction.UKNOWN : Direction.valueOf(directionString);
    }

    public State getState() {
        return State.valueOf(stateString);
    }
}
