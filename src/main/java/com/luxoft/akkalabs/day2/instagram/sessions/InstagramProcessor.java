package com.luxoft.akkalabs.day2.instagram.sessions;

import akka.actor.ActorContext;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import com.luxoft.akkalabs.day2.sessions.SessionProcessor;
import com.luxoft.akkalabs.day2.topics.messages.SubscribeToTopic;
import com.luxoft.akkalabs.day2.topics.messages.UnsubscribeFromTopic;

import javax.websocket.Session;
import java.io.IOException;

public class InstagramProcessor implements SessionProcessor {
    private String sessionId;
    private ActorContext context;
    private Session session;

    @Override
    public void started(String sessionId, ActorContext context, Session session) throws IOException {
        this.sessionId = sessionId;
        this.context = context;
        this.session = session;

        context.system().actorSelection("/user/topics").tell(new SubscribeToTopic("instagram"), context.self());
    }

    @Override
    public void stopped() throws IOException {
        context.system().actorSelection("/user/topics").tell(new UnsubscribeFromTopic("instagram"), context.self());
    }

    @Override
    public void incoming(String message) {
    }

    @Override
    public void outgoing(Object message) throws IOException {
        if (message instanceof TweetObject) {
            for (String url : ((TweetObject) message).getUrls())
                if (url.startsWith("https://instagram.com/p/") && Math.random() < 0.1)
                    context.self().tell(url, context.self());
        } else if (message instanceof String)
            session.getBasicRemote().sendText((String) message);
    }
}
