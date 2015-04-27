package com.luxoft.akkalabs.clients.twitter.search;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.luxoft.akkalabs.clients.twitter.QueueTwitterClient;
import com.luxoft.akkalabs.clients.twitter.hbc.HbcQueueTwitterClient;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchQueueTwitterClient implements QueueTwitterClient {

    private final BlockingQueue<TweetObject> msgQueue;
    private final SearchActorFeedingTwitterClient client;

    public SearchQueueTwitterClient(ActorSystem system, String keyword) {
        this(system, keyword, 1000);
    }

    public SearchQueueTwitterClient(ActorSystem system, String keyword, long delay) {
        msgQueue = new LinkedBlockingQueue<>(100000);
        ActorRef ref = system.actorOf(Props.create(QueueFillerActor.class, msgQueue));
        client = new SearchActorFeedingTwitterClient(system, ref, keyword, delay, true);
    }

    public SearchQueueTwitterClient start() {
        client.start();
        return this;
    }

    @Override
    public void stop() {
        client.stop();
    }

    @Override
    public void close() {
        stop();
    }

    @Override
    public boolean hasNext() {
        return !msgQueue.isEmpty();
    }

    @Override
    public TweetObject next() {
        try {
            return msgQueue.take();
        } catch (InterruptedException ex) {
            Logger.getLogger(HbcQueueTwitterClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static class QueueFillerActor extends UntypedActor {

        private final BlockingQueue<TweetObject> msgQueue;

        public QueueFillerActor(BlockingQueue<TweetObject> msgQueue) {
            this.msgQueue = msgQueue;
        }

        @Override
        public void onReceive(Object message) throws Exception {
            if (message instanceof TweetObject) {
                msgQueue.offer((TweetObject) message, 500, TimeUnit.MILLISECONDS);
            }
        }
    }
}
