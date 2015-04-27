package com.luxoft.akkalabs.day2.sessions.messages;

public class OutgoingToSession implements Outgoing {

    private final String sessionId;
    private final Object message;

    public OutgoingToSession(String sessionId, Object message) {
        this.sessionId = sessionId;
        this.message = message;
    }

    public String getSessionId() {
        return sessionId;
    }

    @Override
    public Object getMessage() {
        return message;
    }
}
