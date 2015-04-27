package com.luxoft.akkalabs.day1.wikipedia2.web;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.luxoft.akkalabs.clients.twitter.TwitterClient;
import com.luxoft.akkalabs.clients.twitter.TwitterClients;
import com.luxoft.akkalabs.day1.wikipedia.TweetLinksActor;
import com.luxoft.akkalabs.day1.wikipedia2.actors.ConnectionsActor;
import com.luxoft.akkalabs.day1.wikipedia2.actors.WikipediaActor2;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Init implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ActorSystem system = ActorSystem.create("AkkaLabs");
        sce.getServletContext().setAttribute("actorSystem", system);

        ActorRef connectionsActor = system.actorOf(Props.create(ConnectionsActor.class), "connections");

        ActorRef wikiActor = system.actorOf(Props.create(WikipediaActor2.class));

        ActorRef tweetLinksActor = system.actorOf(Props.create(TweetLinksActor.class, wikiActor));
        TwitterClient c = TwitterClients.start(system, tweetLinksActor, "wikipedia");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ActorSystem system = (ActorSystem) sce.getServletContext().getAttribute("actorSystem");
        system.shutdown();
    }
}
