package com.luxoft.akkalabs.day1.futures;

import akka.actor.ActorSystem;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.luxoft.akkalabs.clients.twitter.TweetObject;

import javax.annotation.Nullable;
import java.util.Map;

public class AppleVsGoogle {

    public static void main(String[] args) throws Exception {
        final ActorSystem system = ActorSystem.create("AppleVsGoogle");
        final CollectTweets apple = new CollectTweets(system, "Apple");
        final CollectTweets google = new CollectTweets(system, "Google");
        final Result appleResult = apple.call();
        final Result googleResult = google.call();
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
            System.out.println( lang + " " + index.get(lang).size());
        }
    }
}
