package com.luxoft.akkalabs.day1.wikipedia;

import akka.actor.UntypedActor;
import com.google.common.base.Optional;
import com.luxoft.akkalabs.clients.wikipedia.WikipediaClient;
import com.luxoft.akkalabs.clients.wikipedia.WikipediaPage;

import java.net.MalformedURLException;
import java.net.URL;

public class WikipediaActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            final String url = (String) message;
            for (String title : getOptTitle(url).asSet())
                System.out.println(title);
        }
    }

    private static Optional<String> getOptTitle(String message) throws MalformedURLException {
        final URL url = new URL(message);
        if (url.getHost().toLowerCase().endsWith(".wikipedia.org") && url.getPath().length() > 6) {
            final WikipediaPage page = WikipediaClient.getPage(url.getHost().substring(0, 2), url.getPath().substring(6));
            return page != null ? Optional.of(page.getTitle()) : Optional.<String>absent();
        } else return Optional.absent();
    }
}
