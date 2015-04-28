package com.luxoft.akkalabs.day2.trending.actors;

import akka.actor.UntypedActor;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import com.luxoft.akkalabs.day2.trending.messages.UpvoteTrending;
import scala.concurrent.duration.FiniteDuration;

import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;

public class TrendingCalculatorActor extends UntypedActor {
    private static final Object PING = new Object();

    private final Map<String, Integer> words = new HashMap<>();

    @Override
    public void preStart() throws Exception {
        final FiniteDuration oneSecond = FiniteDuration.create(1, SECONDS);
        context().system().scheduler().schedule(oneSecond, oneSecond,
                self(), PING,
                context().dispatcher(), self());
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
    }


    @Override
    public void onReceive(Object message) throws Exception {
        if (message == PING) {
        } else if (message instanceof TweetObject) {
            final FluentIterable<String> ws = FluentIterable.of(((TweetObject) message).getText().split(" ")).filter(new Predicate<String>() {
                @Override
                public boolean apply(String input) {
                    return input.length() > 3 && !input.startsWith("http://") && !input.startsWith("https://");
                }
            });
            for (String w : ws)
                addPoints(w, 1);
        } else if (message instanceof UpvoteTrending)
            for (String w : ((UpvoteTrending) message).getKeyword().split(" "))
                addPoints(w, 5);
    }

    private void addPoints(String w, int points) {
        Integer i = words.get(w);
        words.put(w, (i != null ? i : 0) + points);
    }
}
