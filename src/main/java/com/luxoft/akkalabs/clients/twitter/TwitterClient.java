package com.luxoft.akkalabs.clients.twitter;

import java.io.Closeable;

public interface TwitterClient extends Closeable {

    public void stop();

    public void close();
}
