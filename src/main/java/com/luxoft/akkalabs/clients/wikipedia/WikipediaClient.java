package com.luxoft.akkalabs.clients.wikipedia;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class WikipediaClient {

    public static WikipediaPage getPage(String name) {
        return getPage("en", name);
    }

    public static WikipediaPage getPage(String langCode, String name) {
        try {
            URL url = new URL("http://" + langCode + ".wikipedia.org/w/api.php?action=parse&format=json&prop=text&section=0&page=" + name);
            JSONObject data = new JSONObject(readString(url.openStream()));
            if (data.has("parse")) {
                JSONObject page = data.getJSONObject("parse");
                if (page.has("title") && page.has("text")) {
                    String title = page.getString("title");
                    JSONObject text = page.getJSONObject("text");
                    return new WikipediaPage(title, text.getString("*"));
                }
            }
        } catch (IOException ex) {
        }
        return null;
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
