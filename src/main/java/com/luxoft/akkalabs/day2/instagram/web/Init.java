package com.luxoft.akkalabs.day2.instagram.web;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import com.luxoft.akkalabs.day2.instagram.actors.InstagramActor;
import com.luxoft.akkalabs.day2.instagram.sessions.InstagramProcessor;
import com.luxoft.akkalabs.day2.sessions.actors.SessionsHubActor;
import com.luxoft.akkalabs.day2.sessions.websocket.WebSocketLauncher;
import com.luxoft.akkalabs.day2.topics.actors.TwitterTopicActor;
import com.luxoft.akkalabs.day2.topics.actors.TwitterTopicsHubActor;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Init implements ServletContextListener {

    public static final String ACTOR_SYSTEM_KEY = "instagramActorSystem";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ActorSystem system = ActorSystem.create("Instagram");
        sce.getServletContext().setAttribute(ACTOR_SYSTEM_KEY, system);

        system.actorOf(Props.create(TwitterTopicsHubActor.class, TwitterTopicActor.class), "topics");
        system.actorOf(Props.create(SessionsHubActor.class, InstagramProcessor.class), "sessions");

        system.actorOf(new RoundRobinPool(8).props(Props.create(InstagramActor.class)), "instagram");

        WebSocketLauncher.launchSessionEndpoint(sce.getServletContext(), "/day2/instagram", ACTOR_SYSTEM_KEY);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ActorSystem system = getSystem(sce.getServletContext());
        system.shutdown();
    }

    public static ActorSystem getSystem(ServletContext context) {
        return (ActorSystem) context.getAttribute(ACTOR_SYSTEM_KEY);
    }
}
