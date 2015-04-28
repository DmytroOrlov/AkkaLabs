package com.luxoft.akkalabs.day2.sessions.actors;

import akka.actor.UntypedActor;
import com.luxoft.akkalabs.day2.sessions.SessionProcessor;
import com.luxoft.akkalabs.day2.sessions.messages.Incoming;
import com.luxoft.akkalabs.day2.sessions.messages.Outgoing;

import javax.websocket.MessageHandler;
import javax.websocket.Session;

public class SessionActor extends UntypedActor {
//    private final Session session;
//    public SessionActor(Session session) {
//        this.session = session;
//    }

    private final String sessionId;
    private final Session session;
    private final SessionProcessor processor;

    public SessionActor(String sessionId, Session session, SessionProcessor processor) {
        this.sessionId = sessionId;
        this.session = session;
        this.processor = processor;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Incoming) {
            processor.incoming(((Incoming) message).getMessage());
        } else if (message instanceof Outgoing) {
            processor.outgoing(((Outgoing) message).getMessage());
        } else {
            processor.outgoing(message);
        }
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        session.addMessageHandler(new MessageHandler.Whole<String>() {
            @Override
            public void onMessage(String msg) {
                self().tell(new Incoming(msg), self());
            }
        });
        processor.started(sessionId, context(), session);
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        processor.stopped();
    }
}
