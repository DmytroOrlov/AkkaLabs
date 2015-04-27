package com.luxoft.akkalabs.day2.sessions.actors;

import akka.actor.UntypedActor;
import com.luxoft.akkalabs.day2.sessions.SessionProcessor;
import com.luxoft.akkalabs.day2.sessions.messages.OutgoingBroadcast;
import com.luxoft.akkalabs.day2.sessions.messages.OutgoingToSession;
import com.luxoft.akkalabs.day2.sessions.messages.RegisterSession;
import com.luxoft.akkalabs.day2.sessions.messages.UnregisterSession;

public class SessionsHubActor extends UntypedActor {

    private final Class<? extends SessionProcessor> processorClass;

    public SessionsHubActor(Class<? extends SessionProcessor> processor) {
        this.processorClass = processor;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof RegisterSession) {
            //...
        } else if (message instanceof UnregisterSession) {
            //...
        } else if (message instanceof OutgoingToSession) {
            //...
        } else if (message instanceof OutgoingBroadcast) {
            //...
        }
    }
}
