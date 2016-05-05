package com.stxnext.volontulo.ui.login;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.api.User;
import com.stxnext.volontulo.logic.im.ImService;
import com.stxnext.volontulo.logic.session.Session;
import com.stxnext.volontulo.logic.session.SessionManager;
import com.stxnext.volontulo.ui.main.MainHostActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginFragment extends VolontuloBaseFragment implements SessionManager.OnSessionStateChanged {
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
        final SessionManager manager = SessionManager.getInstance(getActivity());
        manager.addOnStateChangedListener(this);
        manager.authenticate(login, password);
    }

    @Override
    public void onSessionStateChanged(Session session) {
        if (session.isAuthenticated()) {
            final Intent startImService = new Intent(getActivity(), ImService.class);
            getActivity().startService(startImService);

            final Intent startMainActivity = new Intent(getActivity(), MainHostActivity.class);
            startActivity(startMainActivity);
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), R.string.error_wrong_email_or_password, Toast.LENGTH_SHORT).show();
        }
        SessionManager.getInstance(getActivity()).removeOnStateChangedListener(this);
    }
}
