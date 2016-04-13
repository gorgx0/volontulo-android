package com.stxnext.volontulo.logic.im;

public class Message {
    public enum Direction {
        RECEIVED,
        SENT
        ;
        private static Direction[] cachedValues = null;

        public static Direction fromInt(int value) {
            if (cachedValues == null) {
                cachedValues = values();
            }
            return cachedValues[value];
        }
    }

    public Message(String body, Direction direction) {
        this.bodyText = body;
        this.direction = direction;
    }

    private Direction direction;
    private String bodyText;

    public String getText() {
        return bodyText;
    }

    public Direction getDirection() {
        return direction;
    }
}
