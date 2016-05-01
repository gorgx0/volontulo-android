package com.stxnext.volontulo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.stxnext.volontulo.logic.im.config.ImConfigFactory;
import com.stxnext.volontulo.ui.login.LoginActivity;
import com.stxnext.volontulo.ui.main.MainHostActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 3000;
    private static final int QUICK_TIMEOUT = 500;
    private boolean isLogged;
    private int timeout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogged = VolontuloApp.sessionUser.isLogged();
        timeout = isLogged ? QUICK_TIMEOUT : SPLASH_TIMEOUT;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent startMain;
                if (SplashActivity.this.isLogged) {
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
