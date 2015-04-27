package com.luxoft.akkalabs.day2.sessions.messages;

public class Incoming {

    private final String message;

    public Incoming(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
