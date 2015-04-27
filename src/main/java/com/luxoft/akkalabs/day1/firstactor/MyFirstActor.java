package com.luxoft.akkalabs.day1.firstactor;

import akka.actor.UntypedActor;

/**
 * Created by dorlov on 27/4/15.
 */
public class MyFirstActor extends UntypedActor {
    @Override
    public void onReceive(Object message) throws Exception {
        if ("ping".equals((String) message))
            System.out.println("Got ping.");
    }
}
