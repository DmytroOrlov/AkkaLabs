package com.luxoft.akkalabs.day1.firstactor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class FirstActorApp {

    public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create("FirstActorApp");
        final ActorRef actor = system.actorOf(Props.create(MyFirstActor.class), "myFirstActor");
        actor.tell("ping", null);

        Thread.sleep(200);
        system.shutdown();
    }
}
