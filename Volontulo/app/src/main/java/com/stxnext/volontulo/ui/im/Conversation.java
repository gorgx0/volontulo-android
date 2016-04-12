package com.stxnext.volontulo.ui.im;

public class Conversation {
    private String nickname;

    public Conversation(String participantName) {
        nickname = participantName;
    }

    public String getNickname() {
        return nickname;
    }

}
