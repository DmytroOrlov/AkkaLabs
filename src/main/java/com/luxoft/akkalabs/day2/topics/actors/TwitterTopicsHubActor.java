package com.luxoft.akkalabs.day2.topics.actors;

import com.luxoft.akkalabs.day2.topics.messages.*;
import akka.actor.UntypedActor;

public class TwitterTopicsHubActor extends UntypedActor {

    private final Class<? extends UntypedActor> topicClass;

    public TwitterTopicsHubActor(Class<? extends UntypedActor> topicClass) {
        this.topicClass = topicClass;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof SubscribeToTopic) {

        } else if (message instanceof UnsubscribeFromTopic) {

        } else if (message instanceof TopicIsEmpty) {
            
        }
    }
}
