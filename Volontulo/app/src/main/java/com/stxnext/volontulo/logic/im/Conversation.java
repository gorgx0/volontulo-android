package com.stxnext.volontulo.logic.im;

import com.stxnext.volontulo.ui.login.LoginFragment;
import com.stxnext.volontulo.utils.realm.RealmString;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Conversation extends RealmObject {
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

    public Conversation() {
    }

    public Conversation(LoginFragment.User user) {
        this.user = user;
    }

    public String getConversationId() {
        return conversationId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public List<RealmString> getRecipientsIds() {
        return recipientsIds;
    }

    @Ignore
    private LoginFragment.User user;

    public String getNickname() {
        return user.getSurname();
    }

    public LoginFragment.User asUser() {
        return user;
    }

}
