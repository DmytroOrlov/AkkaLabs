package com.luxoft.akkalabs.clients.instagram;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class InstagramClient {

    public static String pageToImageUrl(String page) {
        try {
            URL url = new URL("http://api.instagram.com/oembed?url=" + page);
            String reply = readString(url.openStream());
            JSONObject json = new JSONObject(reply);
            return json.optString("url", json.optString("thumbnail_url", null));
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static String readString(InputStream in) throws IOException {
        try (BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            String str = null;
            StringBuilder sb = new StringBuilder(8192);
            while ((str = r.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        }
    }
}
