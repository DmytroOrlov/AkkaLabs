package com.luxoft.akkalabs.day1.wikipedia2.actors;

import akka.actor.UntypedActor;
import akka.dispatch.Futures;
import com.luxoft.akkalabs.clients.wikipedia.WikipediaClient;
import com.luxoft.akkalabs.clients.wikipedia.WikipediaPage;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.Deliver;

import javax.annotation.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class WikipediaActor2 extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String)
            createFuturePage((String) message);
    }

    public void createFuturePage(String message) throws MalformedURLException {
        final URL url = new URL(message);
        if (url.getHost().toLowerCase().endsWith(".wikipedia.org") && url.getPath().length() > 6)
            Futures.future(new Callable<WikipediaPage>() {
                @Override
                public WikipediaPage call() throws Exception {
                    final WikipediaPage page = getWikipediaPage(url);
                    if (page != null)
                        context().system().actorSelection("/user/connections").
                                tell(new Deliver(page), self());
                    return page;
                }
            }, context().dispatcher());
    }

    @Nullable
    private static WikipediaPage getWikipediaPage(URL url) {
        return WikipediaClient.getPage(url.getHost().substring(0, 2), url.getPath().substring(6));
    }
}
