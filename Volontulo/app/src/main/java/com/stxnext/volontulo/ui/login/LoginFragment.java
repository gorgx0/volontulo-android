package com.stxnext.volontulo.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.logic.im.ImService;
import com.stxnext.volontulo.logic.im.config.ImConfigFactory;
import com.stxnext.volontulo.ui.main.MainHostActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginFragment extends VolontuloBaseFragment {
    @Bind(R.id.edit_text_email)
    EditText editTextEmail;

    @Bind(R.id.edit_text_password)
    EditText editTextPassword;

    public static final User[] MOCK_USER_TABLE = new User[]{
        createUser("test@test.fm", "test"),
        createUser("bob@top.com", "bob"),
        createUser("alone@test.lt", "test")
    };

    private static User createUser(String login, String secret) {
        final User user = new User();
        user.setEmail(login);
        user.setUsername(login);
        user.secret = secret;
        return user;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_login;
    }

    @OnClick(R.id.button_login)
    public void doLogin() {
        final String login = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();
        if (checkCredentials(login, password)) {
            storeUserInfo(login);
            Intent intent = new Intent(getActivity(), MainHostActivity.class);
            getActivity().startService(new Intent(getActivity(), ImService.class));
            startActivity(intent);
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), R.string.error_wrong_email_or_password, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkCredentials(String login, String password) {
        for (final User user : MOCK_USER_TABLE) {
            if (user.getEmail().equals(login) && user.secret.equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void storeUserInfo(CharSequence text) {
        final String preferencesFileName = ImConfigFactory.create().getPreferencesFileName();
        final SharedPreferences preferences = getActivity().getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE);
        preferences.edit()
            .putString("user", String.valueOf(text))
            .putBoolean(getString(R.string.preference_key_is_logged), true)
            .apply();
    }
}
