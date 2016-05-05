package com.stxnext.volontulo.ui.login;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.stxnext.volontulo.R;
import com.stxnext.volontulo.VolontuloApp;
import com.stxnext.volontulo.VolontuloBaseFragment;
import com.stxnext.volontulo.logic.im.ImService;
import com.stxnext.volontulo.ui.main.MainHostActivity;
import com.stxnext.volontulo.ui.utils.SessionUser;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginFragment extends VolontuloBaseFragment implements SessionUser.LoginFinish {
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
        final String login = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();
        VolontuloApp.sessionUser.registerLoginFinish(this);
        checkCredentials(login, password);
    }

    private void checkCredentials(String login, String password) {
        SessionUser sessionUser = VolontuloApp.sessionUser;
        sessionUser.login(login, password);
    }

    @Override
    public void onLoginFinished() {
        if (VolontuloApp.sessionUser.isLogged()) {
            Intent intent = new Intent(getActivity(), MainHostActivity.class);
            getActivity().startService(new Intent(getActivity(), ImService.class));
            startActivity(intent);
            getActivity().finish();
        } else {
            Toast.makeText(getActivity(), R.string.error_wrong_email_or_password, Toast.LENGTH_SHORT).show();
        }
        VolontuloApp.sessionUser.unregisterLoginFinish();
    }
}
