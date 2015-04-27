package com.luxoft.akkalabs.clients.wikipedia;

import org.json.JSONObject;

public class WikipediaPage {

    private final String title;
    private final String text;

    public WikipediaPage(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String toJSONString() {
        JSONObject dataObject = new JSONObject();
        dataObject.put("title", title);
        dataObject.put("text", text);
        return dataObject.toString();
    }
}
