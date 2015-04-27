package com.luxoft.akkalabs.day1.wikipedia2.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.dispatch.Futures;
import akka.dispatch.OnSuccess;
import com.google.common.base.Optional;
import com.luxoft.akkalabs.clients.wikipedia.WikipediaClient;
import com.luxoft.akkalabs.clients.wikipedia.WikipediaPage;
import com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics.Deliver;
import scala.concurrent.Future;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;

public class WikipediaActor2 extends UntypedActor {

    private final ActorRef connections;

    public WikipediaActor2(ActorRef connections) {
        this.connections = connections;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String)
            for (Future<Optional<WikipediaPage>> future : getPage((String) message).asSet())
                future.onSuccess(new OnSuccess<Optional<WikipediaPage>>() {
                    @Override
                    public void onSuccess(Optional<WikipediaPage> result) throws Throwable {
                        for (WikipediaPage page : result.asSet())
                            connections.tell(new Deliver(page), self());
                    }
                }, context().dispatcher());
    }

    public Optional<Future<Optional<WikipediaPage>>> getPage(String message) throws MalformedURLException {
        final URL url = new URL(message);
        return url.getHost().toLowerCase().endsWith(".wikipedia.org") && url.getPath().length() > 6 ?
                Optional.of(Futures.future(new Callable<Optional<WikipediaPage>>() {
                    @Override
                    public Optional<WikipediaPage> call() throws Exception {
                        return getWikipediaPage(url);
                    }
                }, context().dispatcher())) :
                Optional.<Future<Optional<WikipediaPage>>>absent();
    }

    private static Optional<WikipediaPage> getWikipediaPage(URL url) {
        return Optional.fromNullable(
                WikipediaClient.getPage(url.getHost().substring(0, 2), url.getPath().substring(6)));
    }
}
