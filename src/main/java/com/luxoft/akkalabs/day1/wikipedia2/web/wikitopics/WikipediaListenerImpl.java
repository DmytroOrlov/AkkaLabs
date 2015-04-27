package com.luxoft.akkalabs.day1.wikipedia2.web.wikitopics;

import com.luxoft.akkalabs.clients.wikipedia.WikipediaPage;

import javax.servlet.AsyncContext;
import java.io.PrintWriter;

/**
 * Created by dorlov on 27/4/15.
 */
public class WikipediaListenerImpl implements WikipediaListener {
    private final String streamId;
    private final AsyncContext context;

    private PrintWriter writer;

    public WikipediaListenerImpl(String streamId, AsyncContext context) {
        this.streamId = streamId;
        this.context = context;
    }

    @Override
    public void deliver(WikipediaPage page) throws NotDeliveredException {
        try {
            if (writer == null)
                writer = context.getResponse().getWriter();
            writer.append("id:\n");
            for (String s : page.toJSONString().split("\n"))
                writer.append("data: ").append(s).append("\n");

            writer.append("\n\n");
            context.getResponse().flushBuffer();
        } catch (Exception e) {
            throw new NotDeliveredException("Cannot get AsyncContext writer." + e.getMessage(), e);
        }
    }

    @Override
    public String getStreamId() {
        return streamId;
    }

    @Override
    public void close() {

    }
}
