package com.luxoft.akkalabs.day1.wikipedia;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.luxoft.akkalabs.clients.twitter.TwitterClients;

public class GrabWikipediaLinksFromTweets {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("GrabWikipediaLinksFromTweets");

        final ActorRef linksActor = system.actorOf(Props.create(WikipediaActor.class));
        final ActorRef tweetActor = system.actorOf(Props.create(TweetLinksActor.class, linksActor));

        TwitterClients.start(system, tweetActor, "wikipedia");
    }
}
