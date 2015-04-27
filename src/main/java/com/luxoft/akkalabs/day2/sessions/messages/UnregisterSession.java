package com.luxoft.akkalabs.day2.sessions.messages;

public class UnregisterSession {

    private final String sessionId;

    public UnregisterSession(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
