package com.luxoft.akkalabs.day1.wikipedia2.actors;

import akka.actor.UntypedActor;

public class WikipediaActor2 extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            //...
        }
    }
}
