package com.stxnext.volontulo.ui.login;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.stxnext.volontulo.VolontuloBaseActivity;

public class LoginActivity extends VolontuloBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            final FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(android.R.id.content, new LoginFragment())
                    .commit();
        }
    }

}
