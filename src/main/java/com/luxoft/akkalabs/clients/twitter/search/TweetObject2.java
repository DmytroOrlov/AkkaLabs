package com.luxoft.akkalabs.clients.twitter.search;

import com.luxoft.akkalabs.clients.twitter.TweetObject;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import twitter4j.Status;
import twitter4j.URLEntity;

public class TweetObject2 extends TweetObject {

    private final Status status;

    public TweetObject2(Status status) {
        super("{}");
        this.status = status;
    }

    @Override
    public String getText() {
        return status.getText();
    }

    @Override
    public String getLanguage() {
        return status.getLang();
    }

    @Override
    public Collection<String> getUrls() {
        URLEntity[] urls = status.getURLEntities();
        Set<String> set = new HashSet<>();
        for (int i = 0; i < urls.length; i++) {
            String url = urls[i].getExpandedURL();
            set.add(url);
        }
        return set;
    }

    @Override
    public String toString() {
        return "TweetObject{" + "tweet=" + getText() + '}';
    }
}
