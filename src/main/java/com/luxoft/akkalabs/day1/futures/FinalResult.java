package com.luxoft.akkalabs.day1.futures;

import java.util.Map;

/**
 * Created by dorlov on 27/4/15.
 */
public class FinalResult {
    private final String keyword;
    private final Map<String, Integer> languages;

    public FinalResult(String keyword, Map<String, Integer> languages) {
        this.keyword = keyword;
        this.languages = languages;
    }

    public String getKeyword() {

        return keyword;
    }

    public Map<String, Integer> getLanguages() {
        return languages;
    }
}
