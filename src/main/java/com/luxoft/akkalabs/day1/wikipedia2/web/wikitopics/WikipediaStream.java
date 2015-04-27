package com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics;

import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import com.luxoft.akkalabs.day1.wikipedia2.web.Init;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet(asyncSupported = true, urlPatterns = {"/day1/wikitopics"})
public class WikipediaStream extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/event-stream");
        resp.setCharacterEncoding("UTF-8");

        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(240000);

        final ActorSystem system = (ActorSystem) getServletContext().getAttribute(Init.ACTOR_SYSTEM);
        final ActorSelection actorSelection = system.actorSelection("/user/connections");
        final String streamId = UUID.randomUUID().toString();
        actorSelection.tell(new Register(new WikipediaListenerImpl(streamId, asyncContext)), null);

        asyncContext.addListener(new MyListener(streamId, actorSelection));
    }

    private static class MyListener implements AsyncListener {
        private final String streamId;
        private final ActorSelection actorSelection;

        public MyListener(String streamId, ActorSelection actorSelection) {
            this.streamId = streamId;
            this.actorSelection = actorSelection;
        }

        private void unregister() {
            actorSelection.tell(new Unregister(streamId), null);
        }

        @Override
        public void onComplete(AsyncEvent asyncEvent) throws IOException {
            unregister();
        }

        @Override
        public void onTimeout(AsyncEvent asyncEvent) throws IOException {
            unregister();
            asyncEvent.getAsyncContext().complete();
        }

        @Override
        public void onError(AsyncEvent asyncEvent) throws IOException {
            unregister();
        }

        @Override
        public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

        }
    }
}
