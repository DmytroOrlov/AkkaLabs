package com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics;

import com.luxoft.akkalabs.clients.wikipedia.WikipediaPage;

public class Deliver {

    private final WikipediaPage page;

    public Deliver(WikipediaPage page) {
        this.page = page;
    }

    public WikipediaPage getPage() {
        return page;
    }
}
