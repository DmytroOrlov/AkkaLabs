package com.luxoft.akkalabs.clients.twitter.search;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.FiniteDuration;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;

public class TwitterClientActor extends UntypedActor {

    private final ActorRef feedTo;
    private final Twitter twitter;
    private final String keyword;
    private final long delay;
    private final boolean randomDelay;
    //
    private Query query;
    private QueryResult result;
    private Iterator<Status> iterator;

    private static final Object PING = new Object();

    public TwitterClientActor(ActorRef feedTo, Twitter twitter, String keyword, long delay, boolean randomDelay) {
        this.feedTo = feedTo;
        this.twitter = twitter;
        this.keyword = keyword;
        this.delay = delay;
        this.query = new Query(keyword);
        this.randomDelay = randomDelay;
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        if (randomDelay) {
            scheduleOnce();
        } else {
            schedule();
        }
    }

    private void scheduleOnce() {
        long d = randomDelay ? (delay / 2 + (long) (delay / 2 * Math.random())) : delay;
        FiniteDuration duration = FiniteDuration.apply(d, TimeUnit.MILLISECONDS);
        context().system().scheduler().scheduleOnce(duration, self(), PING, context().system().dispatcher(), null);
    }

    private void schedule() {
        FiniteDuration duration = FiniteDuration.apply(delay, TimeUnit.MILLISECONDS);
        context().system().scheduler().schedule(duration, duration, self(), PING, context().system().dispatcher(), null);
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (PING == message) {
            if (result == null) {
                result = twitter.search(query);
                iterator = result.getTweets().iterator();
            } else if (iterator.hasNext()) {
                Status tweet = iterator.next();
                TweetObject to = new TweetObject2(tweet);
                feedTo.tell(to, feedTo);
            } else if (result.hasNext()) {
                query = result.nextQuery();
                result = twitter.search(query);
                iterator = result.getTweets().iterator();
            } else {
                context().stop(self());
                return;
            }
            if (randomDelay) {
                scheduleOnce();
            }
        }
    }

}
