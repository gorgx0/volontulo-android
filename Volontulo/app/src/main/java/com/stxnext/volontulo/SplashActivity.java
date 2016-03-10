package com.stxnext.volontulo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent startMain = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(startMain);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }

}
