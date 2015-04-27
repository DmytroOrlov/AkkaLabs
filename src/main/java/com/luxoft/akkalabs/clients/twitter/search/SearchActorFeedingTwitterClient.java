package com.luxoft.akkalabs.clients.twitter.search;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.luxoft.akkalabs.clients.Config;
import com.luxoft.akkalabs.clients.twitter.TwitterClient;
import twitter4j.Twitter;
import twitter4j.auth.AccessToken;

public class SearchActorFeedingTwitterClient implements TwitterClient {

    private final ActorRef feedTo;
    private final String keyword;
    private final long delay;
    private final boolean randomDelay;
    private ActorRef clientActor;

    private final ActorSystem system;
    private static final Twitter twitter;

    static {
        twitter = twitter4j.TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(Config.get("twitter.customer.key"), Config.get("twitter.customer.secret"));
        twitter.setOAuthAccessToken(new AccessToken(Config.get("twitter.access.token"), Config.get("twitter.access.secret")));
    }

    public SearchActorFeedingTwitterClient(ActorSystem system, ActorRef ref, String keyword) {
        this(system, ref, keyword, 1000, true);
    }

    public SearchActorFeedingTwitterClient(ActorSystem system, ActorRef ref, String keyword, long delay) {
        this(system, ref, keyword, delay, true);
    }

    public SearchActorFeedingTwitterClient(ActorSystem system, ActorRef ref, String keyword, long delay, boolean randomDelay) {
        this.system = system;
        this.feedTo = ref;
        this.keyword = keyword;
        this.delay = delay;
        this.randomDelay = randomDelay;
    }

    public SearchActorFeedingTwitterClient start() {
        if (clientActor == null) {
            clientActor = system.actorOf(Props.create(TwitterClientActor.class, feedTo, twitter, keyword, delay, randomDelay));
        }
        return this;
    }

    @Override
    public void stop() {
        if (clientActor != null) {
            system.stop(clientActor);
            clientActor = null;
        }
    }

    @Override
    public void close() {
        stop();
    }
}
