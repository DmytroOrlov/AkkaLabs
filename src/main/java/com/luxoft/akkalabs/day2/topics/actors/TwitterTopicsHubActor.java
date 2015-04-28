package com.luxoft.akkalabs.day2.topics.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.luxoft.akkalabs.day2.topics.messages.StopTopic;
import com.luxoft.akkalabs.day2.topics.messages.SubscribeToTopic;
import com.luxoft.akkalabs.day2.topics.messages.TopicIsEmpty;
import com.luxoft.akkalabs.day2.topics.messages.UnsubscribeFromTopic;

public class TwitterTopicsHubActor extends UntypedActor {

    private final Class<? extends UntypedActor> topicClass;

    public TwitterTopicsHubActor(Class<? extends UntypedActor> topicClass) {
        this.topicClass = topicClass;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof SubscribeToTopic) {
            final String keyword = ((SubscribeToTopic) message).getKeyword();
            ActorRef child = getContext().getChild(keyword);
            if (child == null)
                child = context().actorOf(Props.create(topicClass), keyword);
            child.forward(message, context());
        } else if (message instanceof UnsubscribeFromTopic) {
            final String keyword = ((UnsubscribeFromTopic) message).getKeyword();
            final ActorRef child = getContext().getChild(keyword);
            if (child != null)
                child.forward(message, context());
        } else if (message instanceof TopicIsEmpty) {
            final String keyword = ((TopicIsEmpty) message).getKeyword();
            final ActorRef child = getContext().getChild(keyword);
            if (child != null)
                child.tell(StopTopic.getInstance(), self());
        }
    }
}
