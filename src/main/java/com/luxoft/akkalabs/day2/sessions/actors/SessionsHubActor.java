package com.luxoft.akkalabs.day2.sessions.actors;

import akka.actor.Props;
import akka.actor.UntypedActor;
import com.luxoft.akkalabs.day2.sessions.SessionProcessor;
import com.luxoft.akkalabs.day2.sessions.messages.OutgoingBroadcast;
import com.luxoft.akkalabs.day2.sessions.messages.OutgoingToSession;
import com.luxoft.akkalabs.day2.sessions.messages.RegisterSession;
import com.luxoft.akkalabs.day2.sessions.messages.UnregisterSession;

import javax.websocket.Session;

public class SessionsHubActor extends UntypedActor {

    private final Class<? extends SessionProcessor> processorClass;

    public SessionsHubActor(Class<? extends SessionProcessor> processor) {
        this.processorClass = processor;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof RegisterSession) {
            final String sessionId = ((RegisterSession) message).getSessionId();
            final Session session = ((RegisterSession) message).getSession();
            context().actorOf(Props.create(SessionActor.class, sessionId, session, processorClass.newInstance()), sessionId);
        } else if (message instanceof UnregisterSession) {
            final String sessionId = ((UnregisterSession) message).getSessionId();
            context().stop(getContext().getChild(sessionId));
        } else if (message instanceof OutgoingToSession) {
            final String sessionId = ((OutgoingToSession) message).getSessionId();
            getContext().getChild(sessionId).forward(message, context());
        } else if (message instanceof OutgoingBroadcast) {
            getContext().getChild("*").forward(message, context());
        }
    }
}
