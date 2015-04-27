package com.luxoft.akkalabs.day2.trending.actors;

import akka.actor.UntypedActor;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import com.luxoft.akkalabs.day2.trending.messages.UpvoteTrending;

public class TrendingCalculatorActor extends UntypedActor {

    private final Object PING = new Object();
    
    @Override
    public void preStart() throws Exception {
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
    }
    
    

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == PING) {
        } else if (message instanceof TweetObject) {
        
        } else if (message instanceof UpvoteTrending) {

        }
    }
}
