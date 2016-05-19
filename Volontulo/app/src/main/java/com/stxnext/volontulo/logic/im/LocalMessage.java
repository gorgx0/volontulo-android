package com.stxnext.volontulo.logic.im;

import android.text.TextUtils;

import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.messaging.Message;
import com.stxnext.volontulo.utils.realm.RealmString;
import com.stxnext.volontulo.utils.realm.RealmTuple;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Representation message entity in local environment.
 */
public class LocalMessage extends RealmObject {
    public static final String KEY_HEADER_CONVERSATION_ID = "Conversation-Id";

    public static final String FIELD_STATE = "stateString";
    public static final String FIELD_CONVERSATION = "conversation";
    public static final String FIELD_MESSAGE_ID = "messageId";

    /**
     * Enumerate that indicates direction of message, ie. sent or received.
     */
    public enum Direction {
        RECEIVED,
        SENT,
        DELIVERED,
        FAILED,
        UKNOWN;
        private static Direction[] cachedValues = null;

        public static Direction fromInt(int value) {
            if (cachedValues == null) {
                cachedValues = values();
            }
            return cachedValues[value];
        }
    }

    /**
     * Enumerate that indicates state of message, ie. unread or read.
     */
    public enum State {
        UNREAD,
        READ
    }

    @PrimaryKey
    private String messageId;
    private Conversation conversation;
    private RealmList<RealmTuple> messageHeaders;
    private String messageTextBody;
    private String senderId;
    private RealmList<RealmString> recipientIds;
    private Date timestamp;
    private String directionString;
    private String stateString;

    public LocalMessage() {
    }

    /**
     * Creates message based on received message from internet.
     * @param client client which is deliver message
     * @param message incoming message
     * @param conversation conversation which message
     * @return converted message to use in local
     */
    public static LocalMessage createFrom(SinchClient client, Message message, Conversation conversation) {
        if (client == null || message == null || conversation == null) {
            throw new IllegalArgumentException("Message cannot be created from null (client/message/conversation)");
        }
        final LocalMessage result = new LocalMessage();
        result.messageId = message.getMessageId();
        result.messageHeaders = listFromMap(message.getHeaders());
        result.messageTextBody = message.getTextBody();
        result.senderId = message.getSenderId();
        result.recipientIds = createRecipients(message.getRecipientIds());
        result.timestamp = message.getTimestamp();
        result.conversation = conversation;
        final String localUserId = client.getLocalUserId();
        if (!TextUtils.isEmpty(localUserId) && localUserId.equals(message.getSenderId())) {
            result.directionString = Direction.SENT.toString();
            result.stateString = State.READ.toString();
        } else {
            result.directionString = Direction.RECEIVED.toString();
            result.stateString = State.UNREAD.toString();
        }
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

    /**
     * Returns local message id. String id is in an {@link java.util.UUID UUID} format.
     * @return
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Returns conversation that this message belongs.
     * @return
     */
    public Conversation getConversation() {
        return conversation;
    }

    /**
     * Returns list of headers associated with this message.
     * @return
     */
    public RealmList<RealmTuple> getMessageHeaders() {
        return messageHeaders;
    }

    /**
     * Returns text message.
     * @return
     */
    public String getMessageTextBody() {
        return messageTextBody;
    }

    /**
     * Returns user id which send this message.
     * @return
     */
    public String getSenderId() {
        return senderId;
    }

    /**
     * Returns list of users ids which receives this message.
     * @return
     */
    public RealmList<RealmString> getRecipientIds() {
        return recipientIds;
    }

    /**
     * Returns timestamp of this message.
     * @return
     */
    public DateTime getTimestamp() {
        return new DateTime(timestamp);
    }

    /**
     * Returns direction of this message.
     * @return
     *
     * @see Direction
     */
    public Direction getDirection() {
        return TextUtils.isEmpty(directionString) ? Direction.UKNOWN : Direction.valueOf(directionString);
    }

    /**
     * Sets direction of this message.
     * @param direction
     *
     * @see Direction
     */
    public void setDirection(Direction direction) {
        this.directionString = direction.toString();
    }

    /**
     * Returns state of this message.
     * @return
     *
     * @see State
     */
    public State getState() {
        return State.valueOf(stateString);
    }

    /**
     * Sets state of this message.
     * @param state
     *
     * @see State
     */
    public void setState(String state) {
        stateString = state;
    }

    /**
     * Sets read state of this message.
     *
     * @see State#READ
     */
    public void read() {
        setState(State.READ.toString());
    }

    /**
     * Returns human string readable representation of this message.
     * @return
     */
    @Override
    public String toString() {
        return "LocalMessage{" +
                "messageId='" + messageId + '\'' +
                ", conversation=" + conversation +
                ", messageHeaders=" + messageHeaders +
                ", messageTextBody='" + messageTextBody + '\'' +
                ", senderId='" + senderId + '\'' +
                ", recipientIds=" + recipientIds +
                ", timestamp=" + timestamp +
                ", directionString='" + directionString + '\'' +
                ", stateString='" + stateString + '\'' +
                '}';
    }
}
