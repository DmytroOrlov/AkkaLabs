package com.luxoft.akkalabs.day2.topics.actors;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.luxoft.akkalabs.clients.twitter.QueueTwitterClient;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import com.luxoft.akkalabs.clients.twitter.TwitterClients;
import com.luxoft.akkalabs.day2.topics.messages.StopTopic;
import com.luxoft.akkalabs.day2.topics.messages.SubscribeToTopic;
import com.luxoft.akkalabs.day2.topics.messages.TopicIsEmpty;
import com.luxoft.akkalabs.day2.topics.messages.UnsubscribeFromTopic;

import java.util.HashSet;
import java.util.Set;

public class TwitterTopicActor extends UntypedActor {

    private final Set<ActorRef> subscribers = new HashSet<>();
    private final String keyword;

    private QueueTwitterClient twitterClient;

    public TwitterTopicActor(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof TweetObject) {

        } else if (message instanceof SubscribeToTopic) {
            subscribers.add(sender());
        } else if (message instanceof UnsubscribeFromTopic) {
            subscribers.remove(sender());
            if (subscribers.isEmpty())
                getContext().parent().tell(new TopicIsEmpty(keyword), self());
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
