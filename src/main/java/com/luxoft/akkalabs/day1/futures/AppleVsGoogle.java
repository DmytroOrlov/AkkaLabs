package com.luxoft.akkalabs.day1.futures;

import akka.actor.ActorSystem;
import akka.dispatch.Futures;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableListMultimap;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import javax.annotation.Nullable;

import static java.util.concurrent.TimeUnit.SECONDS;

public class AppleVsGoogle {

    public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create("AppleVsGoogle");

        final CollectTweets appleFunction = new CollectTweets(system, "Apple");
        final CollectTweets googleFunction = new CollectTweets(system, "Google");

        final Future<Result> appleFuture = Futures.future(appleFunction, system.dispatcher());
        final Future<Result> googleFuture = Futures.future(googleFunction, system.dispatcher());

        final Result appleResult = Await.result(appleFuture, Duration.create(60, SECONDS));
        final Result googleResult = Await.result(googleFuture, Duration.create(60, SECONDS));

        final ImmutableListMultimap<String, TweetObject> appleIndex = indexLanguage(appleResult);
        final ImmutableListMultimap<String, TweetObject> googleIndex = indexLanguage(googleResult);
        
        print(appleIndex, "Apple");
        print(googleIndex, "Google");
        system.shutdown();
    }

    private static ImmutableListMultimap<String, TweetObject> indexLanguage(Result appleResult) {
        return FluentIterable.from(appleResult.getTweets()).index(new Function<TweetObject, String>() {
            @Nullable
            @Override
            public String apply(TweetObject input) {
                return input.getLanguage();
            }
        });
    }

    private static void print(ImmutableListMultimap<String, TweetObject> index, String title) {
        System.out.println(title + ":");
        for (String lang : index.keySet()) {
            System.out.println(lang + " " + index.get(lang).size());
        }
    }
}
