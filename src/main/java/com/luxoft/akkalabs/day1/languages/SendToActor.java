package com.luxoft.akkalabs.day1.languages;

import akka.actor.ActorRef;
import akka.dispatch.OnSuccess;
import com.luxoft.akkalabs.day1.futures.FinalResult;

/**
 * Created by dorlov on 27/4/15.
 */
public class SendToActor extends OnSuccess<FinalResult> {
    private final ActorRef actor;

    public SendToActor(ActorRef actor) {
        this.actor = actor;
    }

    @Override
    public void onSuccess(FinalResult result) throws Throwable {
        actor.tell(result, null);
    }
}
