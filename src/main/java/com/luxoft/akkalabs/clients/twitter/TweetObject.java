package com.luxoft.akkalabs.clients.twitter;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class TweetObject {

    private final String tweet;
    private final JSONObject jsonTweet;

    public TweetObject(String tweet) {
        this.tweet = tweet;
        this.jsonTweet = new JSONObject(tweet);
    }

    public String getText() {
        return jsonTweet.optString("text", null);
    }

    public String getLanguage() {
        return jsonTweet.optString("lang", null);
    }

    public Collection<String> getUrls() {
        if (jsonTweet.has("entities")) {
            JSONObject entities = jsonTweet.getJSONObject("entities");
            if (entities.has("urls")) {
                JSONArray urls = entities.getJSONArray("urls");
                Set<String> set = new HashSet<>();
                for (int i = 0; i < urls.length(); i++) {
                    String url = urls.getJSONObject(i).getString("expanded_url");
                    set.add(url);
                }
                return set;
            }
        }
        return Collections.emptySet();
    }

    @Override
    public String toString() {
        return "TweetObject{" + "tweet=" + getText() + '}';
    }
}
