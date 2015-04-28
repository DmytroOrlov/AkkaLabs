package com.luxoft.akkalabs.day2.trending.actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import com.luxoft.akkalabs.day2.topics.actors.TwitterTopicActor;
import com.luxoft.akkalabs.day2.topics.messages.SubscribeToTopic;
import com.luxoft.akkalabs.day2.topics.messages.UnsubscribeFromTopic;

import java.util.HashSet;
import java.util.Set;

public class TwitterTopicProxyActor extends UntypedActor {

    private final Set<ActorRef> subscribers = new HashSet<>();

    private final String keyword;

    private ActorRef actor;

    public TwitterTopicProxyActor(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Terminated) {
            context().stop(self()); // todo check
        } else if (!sender().equals(actor)) {
            if (message instanceof SubscribeToTopic) {
                if (subscribers.isEmpty()) {
                    subscribers.add(sender());
                    actor.tell(new SubscribeToTopic(keyword), self());
                } else
                    subscribers.add(sender());
            } else if (message instanceof UnsubscribeFromTopic) {
                subscribers.remove(sender());
                if (subscribers.isEmpty())
                    actor.tell(new UnsubscribeFromTopic(keyword), self());
            } else
                actor.forward(message, context());
        } else {
            if (message instanceof TweetObject) {
                for (ActorRef subscriber : subscribers)
                    subscriber.forward(message, context());

                context().actorSelection("/user/trending").forward(message, context());
            } else {
                context().parent().forward(message, context());
            }
        }
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        actor = context().actorOf(Props.create(TwitterTopicActor.class, keyword));
        context().watch(actor);
    }
}
