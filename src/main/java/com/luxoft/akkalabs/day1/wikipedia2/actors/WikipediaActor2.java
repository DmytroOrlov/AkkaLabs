package com.luxoft.akkalabs.day1.wikipedia2.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.luxoft.akkalabs.day1.wikipedia.WikipediaActor;

public class WikipediaActor2 extends UntypedActor {

    private ActorRef connections;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String)
            for (String title : WikipediaActor.getTitle((String) message).asSet())
                connections.tell(title, self());
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        connections = context().actorOf(Props.create(ConnectionsActor.class));
    }
}
