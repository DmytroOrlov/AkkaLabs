package com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics;

import com.luxoft.akkalabs.clients.wikipedia.WikipediaPage;

public interface WikipediaListener {

    void deliver(WikipediaPage page) throws NotDeliveredException;
    public String getStreamId();
    public void close();
}
