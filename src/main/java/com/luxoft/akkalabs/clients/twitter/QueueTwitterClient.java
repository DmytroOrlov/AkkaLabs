package com.luxoft.akkalabs.clients.twitter;

public interface QueueTwitterClient extends TwitterClient {

    public boolean hasNext();

    public TweetObject next();
}
