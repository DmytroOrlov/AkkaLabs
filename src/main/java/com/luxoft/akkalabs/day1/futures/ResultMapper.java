package com.luxoft.akkalabs.day1.futures;

import akka.dispatch.Mapper;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.luxoft.akkalabs.clients.twitter.TweetObject;

import javax.annotation.Nullable;

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
        final ImmutableMap.Builder<String, Integer> builder = ImmutableMap.builder();
        for (String l : index.keySet())
            builder.put(l, index.get(l).size());
        return new FinalResult(parameter.getKeyword(), builder.build());
    }
}
