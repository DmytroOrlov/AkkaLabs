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

    private static Optional<String> getTitle(String message) throws MalformedURLException {
        for (WikipediaPage page : getPage(message).asSet())
            return Optional.of(page.getTitle());
        return Optional.absent();
    }

    public static Optional<WikipediaPage> getPage(String message) throws MalformedURLException {
        final URL url = new URL(message);
        return url.getHost().toLowerCase().endsWith(".wikipedia.org") && url.getPath().length() > 6 ?
                Optional.fromNullable(WikipediaClient.getPage(url.getHost().substring(0, 2), url.getPath().substring(6))) :
                Optional.<WikipediaPage>absent();
    }
}
