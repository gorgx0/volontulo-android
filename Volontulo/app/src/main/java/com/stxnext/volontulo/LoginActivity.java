package com.stxnext.volontulo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.stxnext.volontulo.ui.offers.OfferActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends VolontuloBaseActivity {

    @Bind(R.id.edit_text_email)
    EditText editTextEmail;

    @Bind(R.id.edit_text_password)
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init(R.string.title_activity_login);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.button_login)
    public void doLogin() {
        if (editTextEmail.getText().toString().equals("test") && editTextPassword.getText().toString().equals("test")) {
            Intent intent = new Intent(this, OfferActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(LoginActivity.this, R.string.error_wrong_email_or_password, Toast.LENGTH_SHORT).show();
        }
    }

}
