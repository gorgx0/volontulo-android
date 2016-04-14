package com.stxnext.volontulo.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.ui.main.MainHostActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginFragment extends VolontuloBaseFragment {
    @Bind(R.id.edit_text_email)
    EditText editTextEmail;

    @Bind(R.id.edit_text_password)
    EditText editTextPassword;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.button_login)
    public void doLogin() {
        if (editTextEmail.getText().toString().equals("test") && editTextPassword.getText().toString().equals("test")) {
            final SharedPreferences preferences = getActivity().getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(getString(R.string.preference_key_is_logged), true);
            editor.apply();
            Intent intent = new Intent(getActivity(), MainHostActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), R.string.error_wrong_email_or_password, Toast.LENGTH_SHORT).show();
        }
    }
}
