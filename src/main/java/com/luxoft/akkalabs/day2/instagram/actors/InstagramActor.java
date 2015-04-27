package com.luxoft.akkalabs.day2.instagram.actors;

import akka.actor.UntypedActor;

public class InstagramActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            //...
        }
    }

}
