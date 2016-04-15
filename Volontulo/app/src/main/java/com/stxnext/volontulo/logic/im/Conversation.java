package com.stxnext.volontulo.logic.im;

import com.stxnext.volontulo.ui.login.LoginFragment;

public class Conversation {
    private LoginFragment.User user;

    public Conversation(LoginFragment.User participant) {
        user = participant;
    }

    public String getNickname() {
        return user.getSurname();
    }

    public LoginFragment.User asUser() {
        return user;
    }

}
