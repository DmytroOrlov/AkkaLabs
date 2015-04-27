package com.luxoft.akkalabs.clients.twitter.hbc;

import com.luxoft.akkalabs.clients.Config;
import com.luxoft.akkalabs.clients.twitter.TwitterClient;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.HosebirdMessageProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import java.io.IOException;
import java.util.Arrays;

public abstract class HbcTwitterClient implements TwitterClient {

    private Client hosebirdClient;
    private final String[] keywords;

    protected HbcTwitterClient(String... keywords) {
        this.keywords = keywords;
    }

    protected abstract HosebirdMessageProcessor processor();

    protected Client client() {
        if (hosebirdClient == null) {
            StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
            hosebirdEndpoint.trackTerms(Arrays.asList(keywords));
            Authentication hosebirdAuth = new OAuth1(
                    Config.get("twitter.consumer.key"),
                    Config.get("twitter.consumer.secret"),
                    Config.get("twitter.access.token"),
                    Config.get("twitter.access.secret"));

            ClientBuilder builder = new ClientBuilder()
                    .name("Hosebird-Client-01") // optional: mainly for the logs
                    .hosts(Constants.STREAM_HOST)
                    .authentication(hosebirdAuth)
                    .endpoint(hosebirdEndpoint)
                    .processor(processor());

            hosebirdClient = builder.build();
            hosebirdClient.connect();
        }
        return hosebirdClient;
    }

    public void stop() {
        client().stop();
    }

    @Override
    public void close() {
        stop();
    }
}
