package com.luxoft.akkalabs.clients.twitter.hbc;

import akka.actor.ActorRef;
import com.twitter.hbc.core.processor.HosebirdMessageProcessor;
import java.util.Arrays;

public class HbcActorFeedingTwitterClient extends HbcTwitterClient {

    private final ActorRef ref;

    private HbcActorFeedingTwitterClient(ActorRef ref, String... keywords) {
        super(keywords);
        System.out.println("STARTING CLIENT for " + Arrays.toString(keywords));
        this.ref = ref;
    }

    @Override
    protected HosebirdMessageProcessor processor() {
        return new ActorFeedingProcessor(ref);
    }

    private HbcActorFeedingTwitterClient start() {
        client();
        return this;
    }

    public static HbcActorFeedingTwitterClient start(ActorRef ref, String... keywords) {
        return new HbcActorFeedingTwitterClient(ref, keywords).start();
    }

}
