package com.luxoft.akkalabs.day2.instagram.actors;

import akka.actor.UntypedActor;
import akka.dispatch.Futures;
import akka.dispatch.OnSuccess;
import com.luxoft.akkalabs.clients.instagram.InstagramClient;

import java.util.concurrent.Callable;

public class InstagramActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            final String pageUrl = (String) message;
            Futures.future(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return InstagramClient.pageToImageUrl(pageUrl);
                }
            }, context().dispatcher()).onSuccess(new OnSuccess<String>() {
                @Override
                public void onSuccess(String result) throws Throwable {
                    sender().tell(result, self());
                }
            }, context().dispatcher());
        }
    }
}
