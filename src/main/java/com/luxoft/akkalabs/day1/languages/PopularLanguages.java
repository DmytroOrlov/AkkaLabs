package com.luxoft.akkalabs.day1.languages;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.luxoft.akkalabs.day1.futures.FinalResult;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;

public class PopularLanguages {

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("PopularLanguages");

        final ActorRef actor = system.actorOf(Props.create(LanguagesCounterActor.class));

        final Timeout timeout = Timeout.apply(1, SECONDS);
        for (int i = 0; i < 30; i++) {
            Thread.sleep(1000);
            final Future<Object> future = Patterns.ask(actor, "get", timeout);
            final Object result = Await.result(future, Duration.create(1, SECONDS));
            if (result instanceof Map) {
                System.out.println(i);
                final Map<String, Integer> map = (Map<String, Integer>) result;
                for (String lang : map.keySet()) {
                    System.out.println(lang + " " + map.get(lang));
                }
            }
        }

        Thread.sleep(30000);
        system.shutdown();
    }
}
