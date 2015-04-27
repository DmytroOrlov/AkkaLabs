package com.luxoft.akkalabs.day1.wikipedia;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.luxoft.akkalabs.clients.twitter.TwitterClient;
import com.luxoft.akkalabs.clients.twitter.TwitterClients;

public class GrabWikipediaLinksFromTweets {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("GrabWikipediaLinksFromTweets");

        ActorRef linksActor = system.actorOf(Props.create(WikipediaActor.class));
        ActorRef tweetsActor = system.actorOf(Props.create(TweetLinksActor.class, linksActor));

        TwitterClient c = TwitterClients.start(system, tweetsActor, "wikipedia");
    }
}
