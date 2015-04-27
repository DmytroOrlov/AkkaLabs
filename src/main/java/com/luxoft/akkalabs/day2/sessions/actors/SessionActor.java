package com.luxoft.akkalabs.day2.sessions.actors;

import akka.actor.UntypedActor;
import com.luxoft.akkalabs.day2.sessions.messages.Incoming;
import com.luxoft.akkalabs.day2.sessions.messages.Outgoing;

public class SessionActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Incoming) {
            //...
        } else if (message instanceof Outgoing) {
            //...
        } else {
            //...
        }
    }
}
