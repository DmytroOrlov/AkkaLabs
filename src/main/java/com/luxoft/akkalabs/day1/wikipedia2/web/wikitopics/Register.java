package com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics;

public class Register {

    private final WikipediaListener listener;

    public Register(WikipediaListener listener) {
        this.listener = listener;
    }

    public WikipediaListener getListener() {
        return listener;
    }
}
