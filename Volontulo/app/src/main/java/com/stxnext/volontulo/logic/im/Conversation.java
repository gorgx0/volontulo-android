package com.stxnext.volontulo.logic.im;

import android.content.Context;
import android.text.TextUtils;

import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.logic.session.SessionManager;
import com.stxnext.volontulo.utils.realm.RealmString;
import com.stxnext.volontulo.utils.realm.RealmStringParcelConverter;
import com.stxnext.volontulo.utils.realm.Realms;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.UUID;

import io.realm.ConversationRealmProxy;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

/**
 * Representation conversation between multiple users.
 */
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

    /**
     * Creates conversation based on external id, creator id and recipients list.
     * @param id Provided conversation id from remote user.
     * @param creator User id which is created this conversation.
     * @param recipients List of participating in conversation users.
     * @return newly created conversation
     */
    public static Conversation create(String id, String creator, RealmList<RealmString> recipients) {
        final Conversation conversation = new Conversation();
        conversation.conversationId = id;
        conversation.creatorId = creator;
        conversation.recipientsIds = new RealmList<>(recipients.toArray(new RealmString[recipients.size()]));
        return conversation;
    }

    /**
     * Creates conversation based on creator id and recipients list.
     * Conversation id is created by {@link java.util.UUID UUID}.
     * @param creator User id which is created this conversation.
     * @param recipients List of participating in conversation users.
     * @return newly created conversation with random id
     */
    public static Conversation create(String creator, RealmList<RealmString> recipients) {
        return create(UUID.randomUUID().toString(), creator, recipients);
    }

    /**
     * Test if provided conversation is empty or null.
     * @param object conversation for test
     * @return {@code true} if conversation is empty or null, {@code false} otherwise.
     */
    public static boolean isEmpty(final Conversation object) {
        return object == null ||
            TextUtils.isEmpty(object.conversationId) ||
            TextUtils.isEmpty(object.creatorId) ||
            checkIfRecipientsEmpty(object.recipientsIds);
    }

    private static boolean checkIfRecipientsEmpty(RealmList<RealmString> recipientsIds) {
        return recipientsIds == null || recipientsIds.isEmpty();
    }

    /**
     * Finding recipient based on conversation.
     * @param context
     * @param conversation
     * @return Resolved user id depends who create conversation.
     */
    public static String resolveRecipientId(Context context, Conversation conversation) {
        final String currentUser = resolveCurrentUserName(context);
        final String recipient = conversation.getRecipientsIds().get(0).getValue();
        final String creator = conversation.getCreatorId();
        return (currentUser.equals(recipient)) ? creator : recipient;
    }

    /**
     * Finding user based on id.
     * @param userId user id
     * @return found username or empty string
     */
    public static String resolveName(String userId) {
        final Realm realm = Realm.getDefaultInstance();
        final User found = realm.where(User.class).equalTo(User.FIELD_ID, Integer.parseInt(userId)).findFirst();
        realm.close();
        return found != null ? found.getEmail() : "";
    }

    private static String resolveCurrentUserName(final Context context) {
        return String.valueOf(SessionManager.getInstance(context).getUserProfile().getUser().getId());
    }

    /**
     * Creates or updates found conversation with specified user.
     * @param context context needed for resolving username
     * @param user user which create conversation with someone
     * @return newly created conversation or existing one.
     */
    public static Conversation createOrUpdate(final Context context, final User user) {
        final Realm realm = Realm.getDefaultInstance();
        final String participantId = String.valueOf(user.getId());
        Conversation conversation = realm.where(Conversation.class)
                .equalTo(Conversation.FIELD_CREATOR_ID, participantId)
                .or().equalTo(String.format("%s.%s", Conversation.FIELD_RECIPIENTS_IDS, RealmString.FIELD_VALUE), participantId)
                .findFirst();
        if (conversation == null) {
            conversation = Conversation.create(resolveCurrentUserName(context), new RealmList<>(new RealmString(participantId)));
            Timber.d("No conversation found, so we create new one: %s", conversation);
        } else {
            Timber.d("Conversation found: %s", conversation);
        }
        realm.close();
        return conversation;
    }

    /**
     * Sets this conversation id.
     * @param conversationId
     */
    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * Sets this conversation user id which creates conversation.
     * @param creatorId
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * Sets this conversation recipient list.
     * @param recipientsIds
     */
    @ParcelPropertyConverter(RealmStringParcelConverter.class)
    public void setRecipientsIds(RealmList<RealmString> recipientsIds) {
        this.recipientsIds = recipientsIds;
    }

    /**
     * Returns conversation id.
     * @return
     */
    public String getConversationId() {
        return conversationId;
    }

    /**
     * Returns creator id.
     * @return
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * Returns recipient ids list.
     * @return
     */
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
