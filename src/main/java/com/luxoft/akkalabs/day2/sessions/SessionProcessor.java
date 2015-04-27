package com.luxoft.akkalabs.day2.sessions;

import akka.actor.ActorContext;
import java.io.IOException;
import javax.websocket.Session;

public interface SessionProcessor {

    public void started(String sessionId, ActorContext context, Session session) throws IOException;

    public void stopped() throws IOException;

    public void incoming(String message) throws IOException;

    public void outgoing(Object message) throws IOException;
}
