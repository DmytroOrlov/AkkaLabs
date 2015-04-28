package com.luxoft.akkalabs.day2.trending.web;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.luxoft.akkalabs.day2.sessions.actors.SessionsHubActor;
import com.luxoft.akkalabs.day2.sessions.websocket.WebSocketLauncher;
import com.luxoft.akkalabs.day2.topics.actors.TwitterTopicsHubActor;
import com.luxoft.akkalabs.day2.trending.actors.TrendingCalculatorActor;
import com.luxoft.akkalabs.day2.trending.actors.TwitterTopicProxyActor;
import com.luxoft.akkalabs.day2.trending.sessions.TrendingSessionProcessor;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Init implements ServletContextListener {

    public static final String ACTOR_SYSTEM_KEY = "trendingActorSystem";
    public static final String ACTOR_SYSTEM_NAME = "Trending";

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ActorSystem system = ActorSystem.create(ACTOR_SYSTEM_NAME);
        sce.getServletContext().setAttribute(ACTOR_SYSTEM_KEY, system);

        system.actorOf(Props.create(TrendingCalculatorActor.class), "tranding");
        system.actorOf(Props.create(TwitterTopicsHubActor.class, TwitterTopicProxyActor.class), "topics");
        system.actorOf(Props.create(SessionsHubActor.class, TrendingSessionProcessor.class), "sessions");

        WebSocketLauncher.launchSessionEndpoint(sce.getServletContext(), "/day2/trending", ACTOR_SYSTEM_KEY);
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
