package com.luxoft.akkalabs.day1.wikipedia2.actors;

import akka.actor.UntypedActor;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConnectionsActor extends UntypedActor {
    private final Map<String, WikipediaListener> wikiListeners = new HashMap<>();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Deliver) {
            final Iterator<WikipediaListener> iterator = wikiListeners.values().iterator();
            while (iterator.hasNext()) {
                final WikipediaListener listener = iterator.next();
                try {
                    listener.deliver(((Deliver) message).page());
                } catch (NotDeliveredException e) {
                    wikiListeners.remove(listener.getStreamId());
                }
            }
        } else if (message instanceof Register) {
            final WikipediaListener listener = ((Register) message).listener();
            wikiListeners.put(listener.getStreamId(), listener);
        } else if (message instanceof Unregister)
            wikiListeners.remove(((Unregister) message).id());
    }
}
