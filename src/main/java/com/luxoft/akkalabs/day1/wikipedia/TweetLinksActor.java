package com.luxoft.akkalabs.day1.wikipedia;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class TweetLinksActor extends UntypedActor {

    private final ActorRef linksActor;

    public TweetLinksActor(ActorRef linksActor) {
        this.linksActor = linksActor;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        //..
    }
}
