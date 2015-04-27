package com.luxoft.akkalabs.clients.twitter;

import com.luxoft.akkalabs.clients.twitter.hbc.HbcActorFeedingTwitterClient;
import com.luxoft.akkalabs.clients.twitter.hbc.HbcQueueTwitterClient;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.luxoft.akkalabs.clients.twitter.search.SearchActorFeedingTwitterClient;
import com.luxoft.akkalabs.clients.twitter.search.SearchQueueTwitterClient;

public abstract class TwitterClients {

    public static QueueTwitterClient start(ActorSystem system, String keyword) {
        return AllClients.startSearchQueueClient(system, keyword);
    }

    public static TwitterClient start(ActorSystem system, ActorRef ref, String keyword) {
        return AllClients.startSearchActorFeedingClient(system, ref, keyword);
    }

    public static class AllClients {

        public static QueueTwitterClient startHbcQueueClient(String... keywords) {
            return HbcQueueTwitterClient.start(keywords);
        }

        public static TwitterClient startHbcActorFeedingClient(ActorSystem system, ActorRef ref, String... keywords) {
            return HbcActorFeedingTwitterClient.start(ref, keywords);
        }

        public static TwitterClient startSearchActorFeedingClient(ActorSystem system, ActorRef ref, String keywords) {
            return new SearchActorFeedingTwitterClient(system, ref, keywords).start();
        }

        public static QueueTwitterClient startSearchQueueClient(ActorSystem system, String keywords) {
            return new SearchQueueTwitterClient(system, keywords).start();
        }

        public static QueueTwitterClient startSearchQueueClient(ActorSystem system, String keywords, long delay) {
            return new SearchQueueTwitterClient(system, keywords, delay).start();
        }
    }
}
