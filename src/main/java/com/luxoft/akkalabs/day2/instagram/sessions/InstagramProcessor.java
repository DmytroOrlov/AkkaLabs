package com.luxoft.akkalabs.day2.instagram.sessions;

import akka.actor.ActorContext;
import com.luxoft.akkalabs.day2.sessions.SessionProcessor;
import java.io.IOException;
import javax.websocket.Session;

public class InstagramProcessor implements SessionProcessor {

    private String sessionId;
    private ActorContext context;
    private Session session;

    @Override
    public void started(String sessionId, ActorContext context, Session session) throws IOException {
        this.sessionId = sessionId;
        this.context = context;
        this.session = session;

    }

    @Override
    public void stopped() throws IOException {
        //...
    }

    @Override
    public void incoming(String message) {
    }

    @Override
    public void outgoing(Object message) throws IOException {
        //...
    }
}
