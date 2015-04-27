package com.luxoft.akkalabs.day1.wikipedia;

import akka.actor.UntypedActor;
import com.luxoft.akkalabs.clients.wikipedia.WikipediaClient;
import com.luxoft.akkalabs.clients.wikipedia.WikipediaPage;

import java.net.URL;

public class WikipediaActor extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            final URL url = new URL((String) message);
            if (url.getHost().toLowerCase().endsWith(".wikipedia.org") && url.getPath().length() > 6) {
                final String lang = url.getHost().substring(0, 2);
                final String term = url.getPath().substring(6);
                final WikipediaPage page = WikipediaClient.getPage(lang, term);
                if (page != null)
                    System.out.println(page.getTitle());
            }
        }
    }
}
