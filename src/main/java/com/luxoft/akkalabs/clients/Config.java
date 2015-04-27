package com.luxoft.akkalabs.clients;

import java.util.Properties;

public class Config {

    private static final Properties properties = new Properties();

    public static String get(String key) {
        return properties.getProperty(key);
    }

    static {
        properties.put("twitter.customer.key", "8BJ9xKA15LfzoTzODgnUGy5Ha");
        properties.put("twitter.customer.secret", "gWMpa3023Jl62tsOXYxGr7xGf6MwW5tY0Y4xckSe6g2Ob9IxVg");
        properties.put("twitter.access.token", "2821418188-Kc1rOn0GfEfBW8DAwHgHOkDPGsxiXudRHSfhMal");
        properties.put("twitter.access.secret", "SUIdoBOion4z1oCa19z2Tmm4FzmLfHF0mzPBYSuXhHgHc");
    }
}
