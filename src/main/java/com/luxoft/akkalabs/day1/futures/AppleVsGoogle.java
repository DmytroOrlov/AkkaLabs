package com.luxoft.akkalabs.day1.futures;

import akka.actor.ActorSystem;
import akka.dispatch.Futures;
import akka.dispatch.OnSuccess;
import scala.Function1;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.runtime.BoxedUnit;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AppleVsGoogle {

    public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create("AppleVsGoogle");

        final CollectTweets appleFunction = new CollectTweets(system, "Apple");
        final CollectTweets googleFunction = new CollectTweets(system, "Google");

        final Future<FinalResult> appleFuture = Futures.future(appleFunction, system.dispatcher()).
                map(new ResultMapper(), system.dispatcher());
        final Future<FinalResult> googleFuture = Futures.future(googleFunction, system.dispatcher()).
                map(new ResultMapper(), system.dispatcher());

        final List<Future<FinalResult>> allFutures = Arrays.asList(appleFuture, googleFuture);

        final Future<Iterable<FinalResult>> sequence = Futures.sequence(allFutures, system.dispatcher());
        sequence.onSuccess(new OnSuccess<Iterable<FinalResult>>() {
            @Override
            public void onSuccess(Iterable<FinalResult> result) throws Throwable {
                print(result);
            }
        }, system.dispatcher());

        Await.result(sequence, Duration.create(60, TimeUnit.SECONDS));

        system.shutdown();
    }

    private static void print(Iterable<FinalResult> results) {
        for (FinalResult result : results) {
            System.out.println(result.getKeyword() + ":");
            for (String lang : result.getLanguages().keySet()) {
                System.out.println(lang + " " + result.getLanguages().get(lang));
            }
        }
    }
}
