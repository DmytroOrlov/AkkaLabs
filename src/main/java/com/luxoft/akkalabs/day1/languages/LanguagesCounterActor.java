package com.luxoft.akkalabs.day1.languages;

import akka.actor.UntypedActor;
import akka.dispatch.Futures;
import com.google.common.collect.ImmutableMap;
import com.luxoft.akkalabs.day1.futures.CollectTweets;
import com.luxoft.akkalabs.day1.futures.FinalResult;
import com.luxoft.akkalabs.day1.futures.Result;
import com.luxoft.akkalabs.day1.futures.ResultMapper;
import scala.concurrent.Future;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dorlov on 27/4/15.
 */
public class LanguagesCounterActor extends UntypedActor {
    private final Map<String, Integer> languages = new HashMap<String, Integer>();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof FinalResult) {
            final Integer curr = languages.get(((FinalResult) message).getKeyword());
            languages.put(((FinalResult) message).getKeyword(),
                    (curr != null ? curr : 0) + ((FinalResult) message).getLanguages().size());
        } else if ("get".equals(message)) {
            getSender().tell(ImmutableMap.copyOf(languages), getSelf());
        }
    }

    @Override
    public void preStart() throws Exception {
        super.preStart();
        final List<String> keywords = Arrays.asList("Google", "Apple", "Android", "iPhone", "Lady Gaga");
        for (String keyword : keywords) {
            final Future<Result> future = Futures.future(new CollectTweets(context().system(), keyword), context().system().dispatcher());
            final Future<FinalResult> mapped = future.map(new ResultMapper(), context().system().dispatcher());
            akka.pattern.Patterns.pipe(mapped, context().dispatcher()).to(getSelf());
        }
    }
}
