package com.luxoft.akkalabs.day2.sessions.websocket;

import akka.actor.ActorSystem;
import com.luxoft.akkalabs.day2.sessions.messages.RegisterSession;
import com.luxoft.akkalabs.day2.sessions.messages.UnregisterSession;
import java.util.UUID;
import javax.websocket.CloseReason;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;

public class SessionEndpoint extends Endpoint {

    private EndpointConfig config;

    private ActorSystem system() {
        System.out.println(config.getUserProperties());
        return (ActorSystem) config.getUserProperties().get("actorSystem");
    }

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("WS open");
        this.config = config;
        String sessionId = UUID.randomUUID().toString();
        session.getUserProperties().put("sessionId", sessionId);
        system().actorSelection("/user/sessions").
                tell(new RegisterSession(sessionId, session), null);
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("WS close");
        String sessionId = (String) session.getUserProperties().get("sessionId");
        if (sessionId != null) {
            system().actorSelection("/user/sessions").
                    tell(new UnregisterSession(sessionId), null);
        }
    }
}
