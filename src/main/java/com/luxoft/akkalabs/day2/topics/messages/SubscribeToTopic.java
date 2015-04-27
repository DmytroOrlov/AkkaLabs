package com.luxoft.akkalabs.day2.topics.messages;

public class SubscribeToTopic {

    private final String keyword;

    public SubscribeToTopic(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
