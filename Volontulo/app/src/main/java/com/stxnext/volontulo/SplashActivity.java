package com.stxnext.volontulo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.stxnext.volontulo.logic.session.SessionManager;
import com.stxnext.volontulo.ui.login.LoginActivity;
import com.stxnext.volontulo.ui.main.MainHostActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 3000;
    private static final int QUICK_TIMEOUT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final boolean isLogged = SessionManager.getInstance(this).isAuthenticated();
        final int timeout = isLogged ? QUICK_TIMEOUT : SPLASH_TIMEOUT;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent startMain;
                if (isLogged) {
                    startMain = new Intent(SplashActivity.this, MainHostActivity.class);
                } else {
                    startMain = new Intent(SplashActivity.this, LoginActivity.class);
                }
                startActivity(startMain);
                finish();
            }
        }, timeout);
    }

}
