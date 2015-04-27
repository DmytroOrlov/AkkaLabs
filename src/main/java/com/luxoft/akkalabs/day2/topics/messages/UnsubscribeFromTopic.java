package com.luxoft.akkalabs.day2.topics.messages;

public class UnsubscribeFromTopic {

    public final String keyword;

    public UnsubscribeFromTopic(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
