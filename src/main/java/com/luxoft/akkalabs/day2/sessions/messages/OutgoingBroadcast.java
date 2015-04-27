package com.luxoft.akkalabs.day2.sessions.messages;

public class OutgoingBroadcast implements Outgoing {

    private final Object message;

    public OutgoingBroadcast(Object message) {
        this.message = message;
    }

    @Override
    public Object getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "OutgoingBroadcast{" + "message=" + message + '}';
    }
}
