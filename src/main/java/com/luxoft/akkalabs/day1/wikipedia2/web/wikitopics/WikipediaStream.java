package com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics;

import java.io.IOException;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        final AsyncContext context = req.startAsync();
        context.setTimeout(240000);
    }

    private static class MyListener implements AsyncListener {

        @Override
        public void onComplete(AsyncEvent asyncEvent) throws IOException {

        }

        @Override
        public void onTimeout(AsyncEvent asyncEvent) throws IOException {

        }

        @Override
        public void onError(AsyncEvent asyncEvent) throws IOException {

        }

        @Override
        public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

        }
    }
}
