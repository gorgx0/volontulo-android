package com.stxnext.volontulo.logic.im;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.WritableMessage;
import com.stxnext.volontulo.logic.im.config.ImConfigFactory;
import com.stxnext.volontulo.logic.im.config.ImConfiguration;

public class IMService extends Service implements SinchClientListener {
    private ImConfiguration configuration = ImConfigFactory.create();
    private final InstantMessaging serviceInterface = new InstantMessaging();
    private SinchClient client = null;
    private MessageClient messageClient = null;

    private Intent broadcastIntent = new Intent(ACTION_VOLONTULO_IM);
    private LocalBroadcastManager localBroadcastManager;

    public static final String ACTION_VOLONTULO_IM = "com.stxnext.volontulo.ImClient";
    public static final String EXTRA_KEY_HAS_CONNECTED = "x-has-conn";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String currentUserId = retrieveCurrentUser();
        if (!TextUtils.isEmpty(currentUserId) && !isIMClientStarted()) {
            startIMClient(currentUserId);
        }
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        return super.onStartCommand(intent, flags, startId);
    }

    private String retrieveCurrentUser() {
        final SharedPreferences preferences = getSharedPreferences(configuration.getPreferencesFileName(), MODE_PRIVATE);
        return preferences.getString("user", "");
    }

    private boolean isIMClientStarted() {
        return client != null && client.isStarted();
    }

    public void startIMClient(String userId) {
        client = Sinch.getSinchClientBuilder()
            .context(this)
            .userId(userId)
            .applicationKey(configuration.getApiKey())
            .applicationSecret(configuration.getSecret())
            .environmentHost(configuration.getEnvironmentHost())
            .build();
        client.addSinchClientListener(this);
        client.setSupportMessaging(true);
        client.setSupportActiveConnectionInBackground(true);
        client.checkManifest();
        client.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serviceInterface;
    }

    @Override
    public void onDestroy() {
        if (client != null) {
            client.stopListeningOnActiveConnection();
            client.terminate();
        }
        super.onDestroy();
    }

    @Override
    public void onClientStarted(SinchClient sinchClient) {
        sinchClient.startListeningOnActiveConnection();
        messageClient = sinchClient.getMessageClient();
        broadcastIntent.putExtra(EXTRA_KEY_HAS_CONNECTED, true);
        localBroadcastManager.sendBroadcast(broadcastIntent);
    }

    @Override
    public void onClientStopped(SinchClient sinchClient) {
        client = null;
    }

    @Override
    public void onClientFailed(SinchClient sinchClient, SinchError sinchError) {
        Log.e("Volontulo-Im", String.format("Client: %s, Error: %d [%s@%s]", sinchClient, sinchError.getCode(), sinchError.getErrorType(), sinchError.getMessage()));
        client = null;
        broadcastIntent.putExtra(EXTRA_KEY_HAS_CONNECTED, false);
        localBroadcastManager.sendBroadcast(broadcastIntent);
    }

    @Override
    public void onRegistrationCredentialsRequired(SinchClient sinchClient, ClientRegistration clientRegistration) {
    }

    @Override
    public void onLogMessage(int i, String s, String s1) {
    }

    public void sendMessage(String recipientUser, String messageBody) {
        if (messageClient != null) {
            final WritableMessage message = new WritableMessage(recipientUser, messageBody);
            messageClient.send(message);
        }
    }

    public void addMessageClientListener(MessageClientListener messageClientListener) {
        if (messageClient != null) {
            messageClient.addMessageClientListener(messageClientListener);
        }
    }

    public void removeMessageClientListener(MessageClientListener messageClientListener) {
        if (messageClient != null) {
            messageClient.removeMessageClientListener(messageClientListener);
        }
    }

    public class InstantMessaging extends Binder {
        public void sendMessage(String recipientUser, String messageBody) {
            IMService.this.sendMessage(recipientUser, messageBody);
        }

        public void addMessageClientListener(MessageClientListener messageClientListener) {
            IMService.this.addMessageClientListener(messageClientListener);
        }

        public void removeMessageClientListener(MessageClientListener messageClientListener) {
            IMService.this.removeMessageClientListener(messageClientListener);
        }

        public boolean isClientStarted() {
            return IMService.this.isIMClientStarted();
        }
    }
}
