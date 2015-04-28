package com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics;

public class JRegister {

    private final WikipediaListener listener;

    public JRegister(WikipediaListener listener) {
        this.listener = listener;
    }

    public WikipediaListener getListener() {
        return listener;
    }
}
