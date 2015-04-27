package com.luxoft.akkalabs.day1.futures;

import akka.dispatch.Mapper;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableListMultimap;
import com.luxoft.akkalabs.clients.twitter.TweetObject;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dorlov on 27/4/15.
 */
public class ResultMapper extends Mapper<Result, FinalResult> {
    @Override
    public FinalResult apply(Result parameter) {
        final ImmutableListMultimap<String, TweetObject> index = FluentIterable.from(parameter.getTweets()).index(new Function<TweetObject, String>() {
            @Nullable
            @Override
            public String apply(TweetObject input) {
                return input.getLanguage();
            }
        });
        final Map<String, Integer> languages = new HashMap<String, Integer>();
        for (String l : index.keySet())
            languages.put(l, index.get(l).size());
        return new FinalResult(parameter.getKeyword(), languages);
    }
}
