package com.luxoft.akkalabs.day2.sessions;

import akka.actor.ActorContext;

import javax.websocket.Session;
import java.io.IOException;

public interface SessionProcessor {

    public void started(String sessionId, ActorContext context, Session session) throws IOException;

    public void stopped() throws IOException;

    public void incoming(String message) throws IOException;

    public void outgoing(Object message) throws IOException;
}
