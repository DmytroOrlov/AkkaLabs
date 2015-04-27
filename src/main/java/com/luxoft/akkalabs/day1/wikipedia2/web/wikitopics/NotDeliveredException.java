package com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics;


public class NotDeliveredException extends Exception {

    public NotDeliveredException(String s, Exception e) {
        super(s, e);
    }
}
