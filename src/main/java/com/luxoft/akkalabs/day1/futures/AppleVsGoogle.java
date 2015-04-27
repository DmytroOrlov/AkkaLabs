package com.luxoft.akkalabs.day1.futures;

import akka.actor.ActorSystem;
import akka.dispatch.Futures;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AppleVsGoogle {

    public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create("AppleVsGoogle");

        final CollectTweets appleFunction = new CollectTweets(system, "Apple");
        final CollectTweets googleFunction = new CollectTweets(system, "Google");

        final Future<FinalResult> appleFuture = Futures.future(appleFunction, system.dispatcher()).map(new ResultMapper(), system.dispatcher());
        final Future<FinalResult> googleFuture = Futures.future(googleFunction, system.dispatcher()).map(new ResultMapper(), system.dispatcher());

        final FinalResult appleResult = Await.result(appleFuture, Duration.create(60, SECONDS));
        final FinalResult googleResult = Await.result(googleFuture, Duration.create(60, SECONDS));

        print(appleResult);
        print(googleResult);
        system.shutdown();
    }

    private static void print(FinalResult res) {
        System.out.println(res.getKeyword() + ":");
        for (String lang : res.getLanguages().keySet()) {
            System.out.println(lang + " " + res.getLanguages().get(lang));
        }
    }
}
