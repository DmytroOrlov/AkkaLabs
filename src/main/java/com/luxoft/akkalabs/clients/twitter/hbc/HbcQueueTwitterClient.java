package com.luxoft.akkalabs.clients.twitter.hbc;

import com.luxoft.akkalabs.clients.twitter.QueueTwitterClient;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import com.twitter.hbc.core.processor.HosebirdMessageProcessor;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HbcQueueTwitterClient extends HbcTwitterClient implements QueueTwitterClient {

    private BlockingQueue<String> msgQueue;

    private HbcQueueTwitterClient(String... keywords) {
        super(keywords);
    }

    @Override
    protected HosebirdMessageProcessor processor() {
        msgQueue = new LinkedBlockingQueue<>(100000);
        return new StringDelimitedProcessor(msgQueue);
    }

    @Override
    public boolean hasNext() {
        return !client().isDone();
    }

    @Override
    public TweetObject next() {
        if (!client().isDone()) {
            try {
                return new TweetObject(msgQueue.take());
            } catch (InterruptedException ex) {
                Logger.getLogger(HbcQueueTwitterClient.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } else {
            return null;
        }
    }

    public static HbcQueueTwitterClient start(String... keywords) {
        return new HbcQueueTwitterClient(keywords);
    }
}
