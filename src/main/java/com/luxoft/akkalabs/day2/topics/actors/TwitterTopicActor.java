package com.luxoft.akkalabs.day2.topics.actors;

import akka.actor.UntypedActor;
import com.luxoft.akkalabs.clients.twitter.QueueTwitterClient;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import com.luxoft.akkalabs.clients.twitter.TwitterClients;
import com.luxoft.akkalabs.day2.topics.messages.StopTopic;
import com.luxoft.akkalabs.day2.topics.messages.SubscribeToTopic;
import com.luxoft.akkalabs.day2.topics.messages.UnsubscribeFromTopic;

public class TwitterTopicActor extends UntypedActor {

    private final String keyword;

    private QueueTwitterClient twitterClient;

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

    @Override
    public void preStart() throws Exception {
        super.preStart();
        twitterClient = TwitterClients.start(context().system(), keyword);
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        twitterClient.stop();
    }
}
