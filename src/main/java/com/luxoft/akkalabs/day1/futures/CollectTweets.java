package com.luxoft.akkalabs.day1.futures;

import akka.actor.ActorSystem;
import com.luxoft.akkalabs.clients.twitter.QueueTwitterClient;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import com.luxoft.akkalabs.clients.twitter.TwitterClients;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;

/**
 * Created by dorlov on 27/4/15.
 */
public class CollectTweets implements Callable<Result> {
    private final String word;
    private final ActorSystem system;

    public CollectTweets(ActorSystem system, String word) {
        this.system = system;
        this.word = word;
    }

    @Override
    public Result call() throws Exception {
        final QueueTwitterClient apple = TwitterClients.start(system, word);
        final ArrayList<TweetObject> list = new ArrayList<TweetObject>();
        try {
            for (int i = 0; i < 10; i++)
                list.add(apple.next());
            return new Result(word, Collections.unmodifiableList(list));
        } finally {
            apple.stop();
        }
    }
}
