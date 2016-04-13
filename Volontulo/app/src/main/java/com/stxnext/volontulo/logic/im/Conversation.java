package com.stxnext.volontulo.logic.im;

public class Conversation {
    private String nickname;

    public Conversation(String participantName) {
        nickname = participantName;
    }

    public String getNickname() {
        return nickname;
    }

}
