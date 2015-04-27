package com.luxoft.akkalabs.day1.languages;

import akka.actor.ActorSystem;
import akka.actor.Props;

public class PopularLanguages {

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("PopularLanguages");

        system.actorOf(Props.create(LanguagesCounterActor.class));

        Thread.sleep(30000);
        system.shutdown();
    }
}
