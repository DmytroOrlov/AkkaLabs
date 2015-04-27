package com.luxoft.akkalabs.day2.sessions.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class SessionEndpointConfigurator extends ServerEndpointConfig.Configurator {

    private final String actorSystemKey;

    public SessionEndpointConfigurator(String actorSystemKey) {
        this.actorSystemKey = actorSystemKey;
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(config, request, response);
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        config.getUserProperties().put(
                "actorSystem",
                httpSession.getServletContext().getAttribute(actorSystemKey));
    }

}
