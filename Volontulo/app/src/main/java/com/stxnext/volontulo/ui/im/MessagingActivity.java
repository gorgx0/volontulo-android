package com.stxnext.volontulo.ui.im;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseActivity;
import com.stxnext.volontulo.logic.im.IMService;

public class MessagingActivity extends VolontuloBaseActivity implements MessagesListFragment.InstantMessagingViewCallback {
    private IMService.InstantMessaging instantMessaging;
    private InstantMessagingConnection serviceConnection = new InstantMessagingConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodrawer);

        final Intent intent = getIntent();
        final Bundle data = getBundleFrom(intent);

        boolean isServiceBound = bindService(new Intent(this, IMService.class), serviceConnection, BIND_AUTO_CREATE);

        if (!isServiceBound) {
            final ServiceDisabledAlertDialogFragment alert = new ServiceDisabledAlertDialogFragment();
            alert.show(getSupportFragmentManager(), "dialog");
            return;
        }

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment messagesFragment = new MessagesListFragment();
        final Bundle args = new Bundle();
        args.putString(MessagesListFragment.KEY_PARTICIPANTS, data.getString(MessagesListFragment.KEY_PARTICIPANTS, ""));
        messagesFragment.setArguments(args);
        fragmentManager.beginTransaction()
                .replace(R.id.content, messagesFragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
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
            instantMessaging = null;
        }
    }
}
