package com.luxoft.akkalabs.day1.wikipedia2.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.luxoft.akkalabs.clients.wikipedia.WikipediaPage;
import com.luxoft.akkalabs.day1.wikipedia.WikipediaActor;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.Deliver;

public class WikipediaActor2 extends UntypedActor {

    private final ActorRef connections;

    public WikipediaActor2(ActorRef connections) {
        this.connections = connections;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String)
            for (WikipediaPage page : WikipediaActor.getPage((String) message).asSet())
                connections.tell(new Deliver(page), self());
    }
}
