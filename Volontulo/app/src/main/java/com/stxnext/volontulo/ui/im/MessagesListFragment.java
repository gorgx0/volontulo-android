package com.stxnext.volontulo.ui.im;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.logic.im.Message;
import com.stxnext.volontulo.ui.login.LoginFragment;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.OnClick;

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
//
//    @Bind(R.id.snackbar_container)
//    protected View snackbarLayout;

    private InstantMessagingViewCallback viewCallback;
    private LoginFragment.User participant;
    private MessagesAdapter messagesAdapter;
    private LinearLayoutManager layoutManager;
    private Snackbar snackbar;
    private int newUnreadMessages = 0;

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
        final Bundle args = getArguments();
        participant = Parcels.unwrap(args.getParcelable(KEY_PARTICIPANTS));
        setToolbarTitle(getResources().getString(R.string.im_conversation_with_title, participant.getSurname()));
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setStackFromEnd(true);
        messagesList.setLayoutManager(layoutManager);
        messagesAdapter = new MessagesAdapter(getActivity());
        messagesList.setAdapter(messagesAdapter);
        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinator_layout);
    }

    public void updateList(Message message) {
        messagesAdapter.updateMessage(message);
        final int positionAdded = messagesAdapter.getItemCount() - 1;
        final int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
        if (lastVisiblePosition == positionAdded - 1) {
            messagesList.scrollToPosition(positionAdded);
            newUnreadMessages = 0;
        } else {
            ++newUnreadMessages;
            final String unreadMessagesString = getResources().getQuantityString(R.plurals.im_new_messages, newUnreadMessages, newUnreadMessages);
            if (snackbar == null) {
                snackbar = Snackbar.make(coordinatorLayout, unreadMessagesString, Snackbar.LENGTH_INDEFINITE);
            }
            snackbar.setAction("Przejdź", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    messagesList.scrollToPosition(positionAdded);
                    newUnreadMessages = 0;
                }
            });
            if (snackbar.isShown()) {
                snackbar.setText(unreadMessagesString);
            } else {
                snackbar.show();
            }
        }
        Log.i("Volontulo-Im", "updateMessageList");
    }
}
