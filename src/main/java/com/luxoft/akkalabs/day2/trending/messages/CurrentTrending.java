package com.luxoft.akkalabs.day2.trending.messages;

import java.util.Collections;
import java.util.List;
import org.json.JSONArray;

public class CurrentTrending {

    private final List<String> words;

    public CurrentTrending(List<String> words) {
        this.words = Collections.unmodifiableList(words);
    }

    public List<String> getWords() {
        return words;
    }

    @Override
    public String toString() {
        return "CurrentTrending{" + "words=" + words + '}';
    }

    public String toJSON() {
        JSONArray o = new JSONArray();
        for (String w : words) {
            o.put(w);
        }
        return o.toString();
    }
}
