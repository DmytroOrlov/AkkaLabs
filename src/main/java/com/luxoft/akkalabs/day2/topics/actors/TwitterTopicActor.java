package com.luxoft.akkalabs.day2.topics.actors;

import com.luxoft.akkalabs.day2.topics.messages.UnsubscribeFromTopic;
import com.luxoft.akkalabs.day2.topics.messages.StopTopic;
import com.luxoft.akkalabs.day2.topics.messages.SubscribeToTopic;
import akka.actor.UntypedActor;
import com.luxoft.akkalabs.clients.twitter.TweetObject;

public class TwitterTopicActor extends UntypedActor {

    private final String keyword;

    public TwitterTopicActor(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof TweetObject) {

        } else if (message instanceof SubscribeToTopic) {
        
        } else if (message instanceof UnsubscribeFromTopic) {

        } else if (message instanceof StopTopic) {

        }
    }
}
