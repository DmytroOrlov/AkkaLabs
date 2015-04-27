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
        if (message instanceof String)
            for (String title : getTitle((String) message).asSet())
                System.out.println(title);
    }

    public static Optional<String> getTitle(String message) throws MalformedURLException {
        final URL url = new URL(message);
        if (url.getHost().toLowerCase().endsWith(".wikipedia.org") && url.getPath().length() > 6) {
            final String lang = url.getHost().substring(0, 2);
            final String term = url.getPath().substring(6);
            final WikipediaPage page = WikipediaClient.getPage(lang, term);
            if (page != null)
                return Optional.of(page.getTitle());
        }
        return Optional.absent();
    }
}
