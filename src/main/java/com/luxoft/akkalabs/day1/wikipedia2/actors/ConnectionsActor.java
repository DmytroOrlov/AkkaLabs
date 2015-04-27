package com.luxoft.akkalabs.day1.wikipedia2.actors;

import akka.actor.UntypedActor;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.Deliver;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.Register;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.Unregister;

public class ConnectionsActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Deliver) {
            //...
        } else if (message instanceof Register) {
            //...
        }
        if (message instanceof Unregister) {
            //...
        }
    }
}
