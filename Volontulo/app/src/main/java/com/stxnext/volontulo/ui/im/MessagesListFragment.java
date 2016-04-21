package com.stxnext.volontulo.ui.im;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.logic.im.Conversation;
import com.stxnext.volontulo.logic.im.LocalMessage;

import butterknife.Bind;
import butterknife.OnClick;
import io.realm.Realm;

public class MessagesListFragment extends VolontuloBaseFragment {
    public static final String KEY_PARTICIPANTS = "participants";

    public interface InstantMessagingViewCallback {
        void onMessageComposed(String recipient, String body);
    }

    @Bind(R.id.list)
    protected RecyclerView messagesList;

    @Bind(R.id.message)
    protected EditText message;

    protected CoordinatorLayout coordinatorLayout;

    private Realm realm;

    private InstantMessagingViewCallback viewCallback;
    private Conversation participant;
    private MessagesAdapter messagesAdapter;
    private LinearLayoutManager layoutManager;
    private Snackbar snackbar;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_message_list;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InstantMessagingViewCallback) {
            viewCallback = (InstantMessagingViewCallback) context;
        }
    }

    @OnClick(R.id.send)
    void onSendClicked() {
        final String messageText = message.getText().toString();
        if (viewCallback != null && participant != null && !TextUtils.isEmpty(messageText)) {
            viewCallback.onMessageComposed(participant.getNickname(), messageText);
            message.setText("");
        }
    }

    @Override
    protected void onPostCreateView(View root) {
        super.onPostCreateView(root);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        messagesList.setLayoutManager(layoutManager);
        messagesAdapter = new MessagesAdapter(getActivity());
        messagesList.setAdapter(messagesAdapter);
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator_layout);
    }

    @Override
    public void onStart() {
        super.onStart();
        realm = Realm.getDefaultInstance();
        final Bundle args = getArguments();
        final String conversationId = args.getString(KEY_PARTICIPANTS);
        participant = realm.where(Conversation.class).equalTo("conversationId", conversationId).findFirst();
        setToolbarTitle(getResources().getString(R.string.im_conversation_with_title, participant.getRecipientsIds().get(0)));
        messagesAdapter.setData(realm.where(LocalMessage.class).findAll());
    }

    @Override
    public void onStop() {
        realm.close();
        super.onStop();
    }

    public void updateSingleMessage(LocalMessage message) {
        messagesAdapter.updateSingle(message);
        final int positionAdded = messagesAdapter.getItemCount() - 1;
        final int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
        if (lastVisiblePosition == positionAdded - 1) {
            messagesList.scrollToPosition(positionAdded);
            message.read();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(message);
            realm.commitTransaction();
        } else {
            final long unreadCount = realm.where(LocalMessage.class).equalTo(LocalMessage.FIELD_STATE, LocalMessage.State.UNREAD.toString()).count();
            final String unreadMessagesString = getResources().getQuantityString(R.plurals.im_new_messages, (int)unreadCount, (int)unreadCount);
            if (snackbar == null) {
                snackbar = Snackbar.make(coordinatorLayout, unreadMessagesString, Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Przejdź", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        messagesList.scrollToPosition(messagesAdapter.getItemCount() - 1);
                    }
                });
                snackbar.setCallback(new Snackbar.Callback() {
                    @Override
                    public void onDismissed(Snackbar bar, int event) {
                        snackbar = null;
                    }
                });
            }
            if (snackbar.isShown()) {
                snackbar.setText(unreadMessagesString);
            } else {
                snackbar.show();
            }
        }
    }
}
