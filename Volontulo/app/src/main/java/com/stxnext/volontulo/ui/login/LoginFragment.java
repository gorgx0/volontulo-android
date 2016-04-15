package com.stxnext.volontulo.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.logic.im.config.ImConfigFactory;
import com.stxnext.volontulo.ui.main.MainHostActivity;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.ParcelProperty;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginFragment extends VolontuloBaseFragment {
    @Bind(R.id.edit_text_email)
    EditText editTextEmail;

    @Bind(R.id.edit_text_password)
    EditText editTextPassword;

    public static final User[] MOCK_USER_TABLE = new User[]{
        new User("test", "Jan Kowalski", "test"),
        new User("bob", "Uncle Bob", "uncle"),
    };

    @Parcel
    public static class User {
        private String nickname;
        private String surname;
        private String secret;

        @ParcelConstructor
        public User(@ParcelProperty("nickname") String id, @ParcelProperty("surname") String name, @ParcelProperty("secret") String pass) {
            nickname = id;
            surname = name;
            secret = pass;
        }

        public String getNickname() {
            return nickname;
        }

        public String getSurname() {
            return surname;
        }

        public String getSecret() {
            return secret;
        }
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
            storeUserInfo(login, password);
            Intent intent = new Intent(getActivity(), MainHostActivity.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), R.string.error_wrong_email_or_password, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkCredentials(String login, String password) {
        for (final User user : MOCK_USER_TABLE) {
            if (user.getNickname().equals(login) && user.getSecret().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void storeUserInfo(CharSequence text, CharSequence secret) {
        final String preferencesFileName = ImConfigFactory.create().getPreferencesFileName();
        final SharedPreferences preferences = getActivity().getSharedPreferences(preferencesFileName, Context.MODE_PRIVATE);
        preferences.edit()
            .putString("user", String.valueOf(text))
            .putString("secret", String.valueOf(secret))
            .apply();
    }
}
