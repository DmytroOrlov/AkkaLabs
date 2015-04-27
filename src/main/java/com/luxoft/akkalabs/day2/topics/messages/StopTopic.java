package com.luxoft.akkalabs.day2.topics.messages;

public class StopTopic {

    private StopTopic() {
    }

    private static final StopTopic instance = new StopTopic();

    public static StopTopic getInstance() {
        return instance;
    }
}
