package com.luxoft.akkalabs.day2.topics.sessions;

import akka.actor.ActorContext;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import com.luxoft.akkalabs.day2.sessions.SessionProcessor;
import com.luxoft.akkalabs.day2.topics.messages.SubscribeToTopic;
import com.luxoft.akkalabs.day2.topics.messages.UnsubscribeFromTopic;

import javax.websocket.Session;
import java.io.IOException;

public class TopicsSessionProcessor implements SessionProcessor {
    private String sessionId;
    private ActorContext context;
    private Session session;

    @Override
    public void started(String sessionId, ActorContext context, Session session) {
        this.sessionId = sessionId;
        this.context = context;
        this.session = session;
    }

    @Override
    public void stopped() {
    }

    @Override
    public void incoming(String message) {
        final String[] tokens = message.split(" ", 2);
        switch (tokens[0]) {
            case "subscribe":
                context.actorSelection("/user/topics").tell(new SubscribeToTopic(tokens[1]), context.self());
                break;
            case "unsubscribe":
                context.actorSelection("/user/topics").tell(new UnsubscribeFromTopic(tokens[1]), context.self());
                break;
        }
    }

    @Override
    public void outgoing(Object message) throws IOException {
        if (message instanceof TweetObject)
            session.getBasicRemote().sendText("tweet " + ((TweetObject) message).getText());
    }
}
