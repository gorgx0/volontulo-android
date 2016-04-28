package com.stxnext.volontulo.logic.im;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.stxnext.volontulo.logic.im.config.ImConfigFactory;
import com.stxnext.volontulo.utils.realm.RealmString;
import com.stxnext.volontulo.utils.realm.RealmStringParcelConverter;
import com.stxnext.volontulo.utils.realm.Realms;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

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
    public static final String FIELD_CREATOR_ID = "creatorId";
    public static final String FIELD_RECIPIENTS_IDS = "recipientsIds";

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

    public static boolean isEmpty(final Conversation object) {
        return object == null ||
            TextUtils.isEmpty(object.conversationId) ||
            TextUtils.isEmpty(object.creatorId) ||
            checkIfRecipientsEmpty(object.recipientsIds);
    }

    private static boolean checkIfRecipientsEmpty(RealmList<RealmString> recipientsIds) {
        return recipientsIds == null || recipientsIds.isEmpty();
    }

    public static String resolveRecipientName(Context context, Conversation conversation) {
        final String currentUser = resolveCurrentUserName(context);
        final String recipient = conversation.getRecipientsIds().get(0).getValue();
        final String creator = conversation.getCreatorId();
        return (currentUser.equals(recipient)) ? creator : recipient;
    }

    public static String resolveSenderName(Context context, Conversation conversation) {
        final String currentUser = resolveCurrentUserName(context);
        final String recipient = conversation.getRecipientsIds().get(0).getValue();
        final String creator = conversation.getCreatorId();
        return (currentUser.equals(recipient)) ? recipient : creator;
    }

    private static String resolveCurrentUserName(Context context) {
        final String preferencesFileName = ImConfigFactory.create().getPreferencesFileName();
        final SharedPreferences preferences = context.getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE);
        return preferences.getString("user", "");
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @ParcelPropertyConverter(RealmStringParcelConverter.class)
    public void setRecipientsIds(RealmList<RealmString> recipientsIds) {
        this.recipientsIds = recipientsIds;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public RealmList<RealmString> getRecipientsIds() {
        return recipientsIds;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "conversationId='" + conversationId + '\'' +
                ", creatorId='" + creatorId + '\'' +
                ", recipientsIds=" + Realms.realmAsNormal(recipientsIds) +
                '}';
    }
}
