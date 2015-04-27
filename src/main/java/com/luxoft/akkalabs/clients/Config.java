package com.luxoft.akkalabs.clients;

import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    public static String get(String key) {
        return properties.getProperty(key);
    }

    static {
        properties.put("twitter.customer.key", "yJmaM7dcTqAIMQjfZpFwoUc02");
        properties.put("twitter.customer.secret", "WH1lpA0qslTjFYH6qIsTRBbUDISpc8t2oMXOuXQepD4Kbr6axm");
        properties.put("twitter.access.token", "797617302-AQxM4rZo5glbJHWNcQLnXCsqo253L6jjoluRFMi4");
        properties.put("twitter.access.secret", "KPbAfMep37SL3PD9qO5R6YB7WLk6aYgARVK3gnA738F07");
    }
}
