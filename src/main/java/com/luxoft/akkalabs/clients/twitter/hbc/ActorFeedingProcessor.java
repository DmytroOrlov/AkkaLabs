package com.luxoft.akkalabs.clients.twitter.hbc;

import akka.actor.ActorRef;
import com.luxoft.akkalabs.clients.twitter.TweetObject;
import com.twitter.hbc.common.DelimitedStreamReader;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.processor.HosebirdMessageProcessor;
import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActorFeedingProcessor implements HosebirdMessageProcessor {

    protected final ActorRef actor;

    private final static Logger logger = LoggerFactory.getLogger(ActorFeedingProcessor.class);
    private final static int DEFAULT_BUFFER_SIZE = 50000;
    private final static int MAX_ALLOWABLE_BUFFER_SIZE = 500000;
    private final static String EMPTY_LINE = "";
    private DelimitedStreamReader reader;

    public ActorFeedingProcessor(ActorRef actor) {
        this.actor = actor;
    }

    @Override
    public void setup(InputStream input) {
        reader = new DelimitedStreamReader(input, Constants.DEFAULT_CHARSET, DEFAULT_BUFFER_SIZE);
    }

    @Override
    public boolean process() throws IOException, InterruptedException {
        String msg = processNextMessage();
        while (msg == null) {
            msg = processNextMessage();
        }
        actor.tell(new TweetObject(msg), null);
        return true;
    }

    @Nullable
    protected String processNextMessage() throws IOException {
        int delimitedCount = -1;
        int retries = 0;
        while (delimitedCount < 0 && retries < 3) {
            String line = reader.readLine();
            if (line == null) {
                throw new IOException("Unable to read new line from stream");
            } else if (line.equals(EMPTY_LINE)) {
                return null;
            }
            try {
                delimitedCount = Integer.parseInt(line);
            } catch (NumberFormatException n) {
// resilience against the occasional malformed message
                logger.warn("Error parsing delimited length", n);
            }
            retries += 1;
        }
        if (delimitedCount < 0) {
            throw new RuntimeException("Unable to process delimited length");
        }
        if (delimitedCount > MAX_ALLOWABLE_BUFFER_SIZE) {
// this is to protect us from nastiness
            throw new IOException("Unreasonable message size " + delimitedCount);
        }
        return reader.read(delimitedCount);
    }
}
