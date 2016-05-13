package com.stxnext.volontulo.ui.im;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.logic.im.Conversation;
import com.stxnext.volontulo.logic.im.ImService;
import com.stxnext.volontulo.logic.im.LocalMessage;

import org.parceler.Parcels;

import timber.log.Timber;

public class MessagingActivity extends VolontuloBaseActivity implements MessagesListFragment.InstantMessagingViewCallback {
    private static final String KEY_CONVERSATION = "conversation-storage";

    private ImService.InstantMessaging instantMessaging;
    private InstantMessagingConnection serviceConnection = new InstantMessagingConnection();
    private Conversation conversation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodrawer);
        init(R.string.im_conversation_list_title);

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment messagesFragment = new MessagesListFragment();
        final Bundle args = new Bundle();
        final Intent intent = getIntent();
        conversation = Parcels.unwrap(intent.getParcelableExtra(MessagesListFragment.KEY_PARTICIPANTS));
        args.putParcelable(MessagesListFragment.KEY_PARTICIPANTS, Parcels.wrap(conversation));
        messagesFragment.setArguments(args);
        fragmentManager.beginTransaction()
                .replace(R.id.content, messagesFragment)
                .commit();

        boolean isServiceBound = bindService(new Intent(this, ImService.class), serviceConnection, BIND_AUTO_CREATE);

        if (!isServiceBound) {
            final ServiceDisabledAlertDialogFragment alert = new ServiceDisabledAlertDialogFragment();
            alert.show(getSupportFragmentManager(), "dialog");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_CONVERSATION, Parcels.wrap(conversation));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        conversation = Parcels.unwrap(savedInstanceState.getParcelable(KEY_CONVERSATION));
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMessageComposed(String recipient, String body) {
        if (instantMessaging != null) {
            instantMessaging.sendMessage(recipient, body, conversation);
        } else {
            Timber.w("Try to compose message when not connected to IM service");
        }
    }

    private class InstantMessagingConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            instantMessaging = (ImService.InstantMessaging) service;
            if (instantMessaging != null) {
                instantMessaging.addMessageClientListener(messageListener);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (instantMessaging != null) {
                instantMessaging.removeMessageClientListener();
                instantMessaging = null;
            }
        }
    }

    private ImService.ImMessageListener messageListener = new ImService.ImMessageListener() {
        @Override
        public void onMessageIncoming(LocalMessage incoming) {
            final MessagesListFragment messagesListFragment = getMessagesListFragment();
            if (messagesListFragment != null) {
                messagesListFragment.updateSingleMessage(incoming);
            }
        }

        @Override
        public void onMessageSent(LocalMessage outgoing) {
            final MessagesListFragment messagesListFragment = getMessagesListFragment();
            if (messagesListFragment != null) {
                messagesListFragment.updateSingleMessage(outgoing);
            }
        }

        @Override
        public void onMessageFailed(LocalMessage failed) {
            final MessagesListFragment messagesListFragment = getMessagesListFragment();
            if (messagesListFragment != null) {
                messagesListFragment.updateStatus(failed);
            }
        }

        @Override
        public void onMessageDelivered(LocalMessage delivered) {
            final MessagesListFragment messagesListFragment = getMessagesListFragment();
            if (messagesListFragment != null) {
                messagesListFragment.updateStatus(delivered);
            }
        }

        private MessagesListFragment getMessagesListFragment() {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            return (MessagesListFragment) fragmentManager.findFragmentById(R.id.content);
        }
    };
}
