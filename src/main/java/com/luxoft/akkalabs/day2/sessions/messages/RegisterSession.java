package com.luxoft.akkalabs.day2.sessions.messages;

import javax.websocket.Session;

public class RegisterSession {

    private final String sessionId;
    private final Session session;

    public RegisterSession(String sessionId, Session session) {
        this.sessionId = sessionId;
        this.session = session;
    }

    public String getSessionId() {
        return sessionId;
    }

    public Session getSession() {
        return session;
    }
}
