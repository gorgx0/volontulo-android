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
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
import com.sinch.android.rtc.messaging.WritableMessage;
import com.stxnext.volontulo.BuildConfig;
import com.stxnext.volontulo.logic.im.config.ImConfigFactory;
import com.stxnext.volontulo.logic.im.config.ImConfiguration;
import com.stxnext.volontulo.utils.realm.Realms;

import java.util.Date;
import java.util.List;
import java.util.Map;

import io.realm.Realm;

public class ImService extends Service implements SinchClientListener {
    private static final String TAG = "Volontulo-Service-Im";
    private ImConfiguration configuration = ImConfigFactory.create();
    private final InstantMessaging serviceInterface = new InstantMessaging();
    private SinchClient client = null;
    private MessageClient messageClient = null;

    private Realm realm;

    private Intent broadcastIntent = new Intent(ACTION_VOLONTULO_IM);
    private LocalBroadcastManager localBroadcastManager;
    private ImMessageListener messageListener;

    public static final String ACTION_VOLONTULO_IM = "com.stxnext.volontulo.ImClient";
    public static final String EXTRA_KEY_HAS_CONNECTED = "x-has-conn";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String currentUserId = retrieveCurrentUser();
        Log.d(TAG, String.format("IM service initializing with user [%s]", currentUserId));
        if (!TextUtils.isEmpty(currentUserId) && !isIMClientStarted()) {
            Log.i(TAG, "IM service not started, so init it.");
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
        if (BuildConfig.DEBUG) {
            client.checkManifest();
        }
        realm = Realm.getDefaultInstance();
        client.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return serviceInterface;
    }

    @Override
    public void onDestroy() {
        if (realm != null) {
            realm.close();
        }
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
        messageClient.addMessageClientListener(new MessageClientListener() {
            @Override
            public void onIncomingMessage(MessageClient messageClient, Message message) {
                Log.i(TAG, String.format("Incoming message %s [from %s, to %s]", message.getMessageId(), message.getSenderId(), message.getRecipientIds()));
                final String headerConversationId = message.getHeaders().get(LocalMessage.KEY_HEADER_CONVERSATION_ID);
                Conversation conversation = realm.where(Conversation.class).equalTo(Conversation.FIELD_CONVERSATION_ID, headerConversationId).findFirst();
                if (conversation == null) {
                    conversation = Conversation.create(headerConversationId, message.getSenderId(), Realms.normalAsRealm(message.getRecipientIds()));
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(conversation);
                    realm.commitTransaction();
                }
                LocalMessage localMessage = realm.where(LocalMessage.class).equalTo(LocalMessage.FIELD_MESSAGE_ID, message.getMessageId()).findFirst();
                if (localMessage == null) {
                    localMessage = LocalMessage.createFrom(client, message, conversation);
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(localMessage);
                    realm.commitTransaction();
                }
                if (messageListener != null) {
                    messageListener.onMessageIncoming(localMessage);
                }
            }

            @Override
            public void onMessageSent(MessageClient messageClient, Message message, String s) {
                Log.i(TAG, String.format("Outcoming message %s [to %s, from %s]", message.getMessageId(), message.getRecipientIds(), message.getSenderId()));
            }

            @Override
            public void onMessageFailed(MessageClient messageClient, Message message, MessageFailureInfo messageFailureInfo) {
                Log.e(TAG, String.format("Sending message %s failed [%s]", message.getMessageId(), messageFailureInfo.getSinchError()));
            }

            @Override
            public void onMessageDelivered(MessageClient messageClient, MessageDeliveryInfo messageDeliveryInfo) {
                Log.d(TAG, String.format("LocalMessage %s delivered [to %s]", messageDeliveryInfo.getMessageId(), messageDeliveryInfo.getRecipientId()));
            }

            @Override
            public void onShouldSendPushData(MessageClient messageClient, Message message, List<PushPair> list) {
                Log.i(TAG, String.format("Should send push data %s [%s]", message.getMessageId(), list));
            }
        });
        broadcastIntent.putExtra(EXTRA_KEY_HAS_CONNECTED, true);
        localBroadcastManager.sendBroadcast(broadcastIntent);
    }

    @Override
    public void onClientStopped(SinchClient sinchClient) {
        client = null;
    }

    @Override
    public void onClientFailed(SinchClient sinchClient, SinchError sinchError) {
        Log.e(TAG, String.format("Client: %s, Error: %d [%s@%s]", sinchClient, sinchError.getCode(), sinchError.getErrorType(), sinchError.getMessage()));
        client = null;
        broadcastIntent.putExtra(EXTRA_KEY_HAS_CONNECTED, false);
        localBroadcastManager.sendBroadcast(broadcastIntent);
    }

    @Override
    public void onRegistrationCredentialsRequired(SinchClient sinchClient, ClientRegistration clientRegistration) {
        Log.i(TAG, String.format("Registration credentials required %s", clientRegistration));
    }

    @Override
    public void onLogMessage(int i, String s, String s1) {
        Log.i(TAG, String.format("Log message: %d | %s | %s", i, s, s1));
    }

    public void sendMessage(String recipientUser, String messageBody, Conversation conversation) {
        if (messageClient != null) {
            if (!Conversation.isEmpty(conversation)) {
                realm.beginTransaction();
                realm.copyToRealmOrUpdate(conversation);
                realm.commitTransaction();
                final WritableMessage message = new WritableMessage(recipientUser, messageBody);
                message.addHeader(LocalMessage.KEY_HEADER_CONVERSATION_ID, conversation.getConversationId());
                realm.beginTransaction();
                final LocalMessage localMessage = LocalMessage.createFrom(client, transformFrom(message), conversation);
                realm.copyToRealmOrUpdate(localMessage);
                realm.commitTransaction();
                messageClient.send(message);
                if (messageListener != null) {
                    messageListener.onMessageSent(localMessage);
                }
            } else {
                Log.w(TAG, "Trying to send message without conversation, something goes wrong");
            }
        } else {
            Log.w(TAG, "Trying to send message when IM client not connected");
        }
    }

    private Message transformFrom(final WritableMessage message) {
        return new Message() {
            @Override
            public String getMessageId() {
                return message.getMessageId();
            }

            @Override
            public Map<String, String> getHeaders() {
                return message.getHeaders();
            }

            @Override
            public String getTextBody() {
                return message.getTextBody();
            }

            @Override
            public List<String> getRecipientIds() {
                return message.getRecipientIds();
            }

            @Override
            public String getSenderId() {
                return client.getLocalUserId();
            }

            @Override
            public Date getTimestamp() {
                return new Date();
            }
        };
    }

    public void addMessageClientListener(ImMessageListener messageClientListener) {
        messageListener = messageClientListener;
    }

    public void removeMessageClientListener() {
        messageListener = null;
    }

    public class InstantMessaging extends Binder {
        public void sendMessage(String recipientUser, String messageBody, Conversation conversation) {
            ImService.this.sendMessage(recipientUser, messageBody, conversation);
        }

        public void addMessageClientListener(ImMessageListener messageListener) {
            ImService.this.addMessageClientListener(messageListener);
        }

        public void removeMessageClientListener() {
            ImService.this.removeMessageClientListener();
        }

        public SinchClient getSinchClient() {
            return ImService.this.client;
        }

        public boolean isClientStarted() {
            return ImService.this.isIMClientStarted();
        }
    }

    public interface ImMessageListener {
        void onMessageIncoming(LocalMessage incoming);

        void onMessageSent(LocalMessage outgoing);

        void onMessageFailed(LocalMessage failed);

        void onMessageDelivered(LocalMessage delivered);
    }
}
