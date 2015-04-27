package com.luxoft.akkalabs.day1.futures;

import com.luxoft.akkalabs.clients.twitter.TweetObject;

import java.util.Collection;

/**
 * Created by dorlov on 27/4/15.
 */
public class Result {
    private final String keyword;
    private final Collection<TweetObject> tweets;

    public Result(String keyword, Collection<TweetObject> tweets) {
        this.keyword = keyword;
        this.tweets = tweets;
    }

    public String getKeyword() {

        return keyword;
    }

    public Collection<TweetObject> getTweets() {
        return tweets;
    }
}
