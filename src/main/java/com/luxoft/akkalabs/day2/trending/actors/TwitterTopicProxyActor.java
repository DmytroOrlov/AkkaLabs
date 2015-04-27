package com.luxoft.akkalabs.day2.trending.actors;

import akka.actor.UntypedActor;

public class TwitterTopicProxyActor extends UntypedActor {

    private final String keyword;

    public TwitterTopicProxyActor(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void onReceive(Object message) throws Exception {

    }
}
