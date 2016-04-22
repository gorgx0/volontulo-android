package com.stxnext.volontulo.logic.im;

import com.stxnext.volontulo.utils.realm.RealmString;
import com.stxnext.volontulo.utils.realm.Realms;

import org.parceler.Parcel;

import java.util.List;
import java.util.UUID;

import io.realm.ConversationRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

@Parcel(implementations = {ConversationRealmProxy.class},
    value = Parcel.Serialization.BEAN,
    analyze = {Conversation.class})
public class Conversation extends RealmObject {
    public static final String FIELD_CONVERSATION_ID = "conversationId";

    @PrimaryKey
    private String conversationId;
    private String creatorId;
    private RealmList<RealmString> recipientsIds;

    public static Conversation create(String id, String creator, RealmList<RealmString> recipients) {
        final Conversation conversation = new Conversation();
        conversation.conversationId = id;
        conversation.creatorId = creator;
        conversation.recipientsIds = new RealmList<>(recipients.toArray(new RealmString[recipients.size()]));
        return conversation;
    }

    public static Conversation create(String creator, RealmList<RealmString> recipients) {
        return create(UUID.randomUUID().toString(), creator, recipients);
    }

    public Conversation() {
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public List<String> getRecipientsIds() {
        return Realms.realmAsNormal(recipientsIds);
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "conversationId='" + conversationId + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", recipientsIds=" + recipientsIds +
                '}';
    }
}
