package com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics;

import com.luxoft.akkalabs.clients.wikipedia.WikipediaPage;

public class JDeliver {

    private final WikipediaPage page;

    public JDeliver(WikipediaPage page) {
        this.page = page;
    }

    public WikipediaPage getPage() {
        return page;
    }
}
