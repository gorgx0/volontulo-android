package com.stxnext.volontulo.ui.im;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.internal.gen.Build;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.logic.im.IMService;
import com.stxnext.volontulo.logic.im.LocalMessage;

import java.util.List;

public class MessagingActivity extends VolontuloBaseActivity implements MessagesListFragment.InstantMessagingViewCallback {
    private IMService.InstantMessaging instantMessaging;
    private InstantMessagingConnection serviceConnection = new InstantMessagingConnection();
    private EventsReceived eventsReceived = new EventsReceived();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodrawer);
        init(R.string.im_conversaion_list_title);

        final Intent intent = getIntent();
        final Bundle data = getBundleFrom(intent);

        LocalBroadcastManager.getInstance(this).registerReceiver(eventsReceived, new IntentFilter(IMService.ACTION_VOLONTULO_IM));
        startService(new Intent(this, IMService.class));
        boolean isServiceBound = bindService(new Intent(this, IMService.class), serviceConnection, BIND_AUTO_CREATE);

        if (!isServiceBound) {
            final ServiceDisabledAlertDialogFragment alert = new ServiceDisabledAlertDialogFragment();
            alert.show(getSupportFragmentManager(), "dialog");
            return;
        }

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment messagesFragment = new MessagesListFragment();
        final Bundle args = new Bundle();
        args.putParcelable(MessagesListFragment.KEY_PARTICIPANTS, data.getParcelable(MessagesListFragment.KEY_PARTICIPANTS));
        messagesFragment.setArguments(args);
        fragmentManager.beginTransaction()
                .replace(R.id.content, messagesFragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        stopService(new Intent(this, IMService.class));
        LocalBroadcastManager.getInstance(this).unregisterReceiver(eventsReceived);
        super.onDestroy();
    }

    private Bundle getBundleFrom(Intent intent) {
        return intent != null ? getExtrasNonNull(intent) : new Bundle();
    }

    private Bundle getExtrasNonNull(Intent intent) {
        return intent.getExtras() != null ? intent.getExtras() : new Bundle();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMessageComposed(String recipient, String body) {
        instantMessaging.sendMessage(recipient, body);
    }

    private class InstantMessagingConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            instantMessaging = (IMService.InstantMessaging) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            if (instantMessaging != null) {
                instantMessaging.removeMessageClientListener(messageClientListener);
                instantMessaging = null;
            }
        }
    }

    private class EventsReceived extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i("Volontulo-Im", "Service started");
            instantMessaging.addMessageClientListener(messageClientListener);
            if (Build.DEBUG) {
                Toast.makeText(context, "Messaging service started and ready", Toast.LENGTH_LONG).show();
            }
        }
    }

    private MessageClientListener messageClientListener = new MessageClientListener() {
        @Override
        public void onIncomingMessage(MessageClient messageClient, Message message) {
            Log.w("Volontulo-Im", String.format("Incoming: %s", message.getMessageId()));
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final MessagesListFragment messagesListFragment = (MessagesListFragment) fragmentManager.findFragmentById(R.id.content);
            if (messagesListFragment != null) {
                messagesListFragment.updateSingleMessage(LocalMessage.createFrom(instantMessaging.getSinchClient(), message));
            }
        }

        @Override
        public void onMessageSent(MessageClient messageClient, Message message, String s) {
            Log.w("Volontulo-Im", String.format("Sent: %s", message.getMessageId()));
            final FragmentManager fragmentManager = getSupportFragmentManager();
            final MessagesListFragment messagesListFragment = (MessagesListFragment) fragmentManager.findFragmentById(R.id.content);
            if (messagesListFragment != null) {
                messagesListFragment.updateSingleMessage(LocalMessage.createFrom(instantMessaging.getSinchClient(), message));
            }
        }

        @Override
        public void onMessageFailed(MessageClient messageClient, Message message, MessageFailureInfo messageFailureInfo) {
            Log.w("Volontulo-Im", String.format("Failed: %s", message.getMessageId()));
        }

        @Override
        public void onMessageDelivered(MessageClient messageClient, MessageDeliveryInfo messageDeliveryInfo) {
            Log.w("Volontulo-Im", String.format("Delivered: %s", messageDeliveryInfo.getMessageId()));
        }

        @Override
        public void onShouldSendPushData(MessageClient messageClient, Message message, List<PushPair> list) {
            Log.w("Volontulo-Im", String.format("SendPush: %s", message.getMessageId()));
        }
    };
}
