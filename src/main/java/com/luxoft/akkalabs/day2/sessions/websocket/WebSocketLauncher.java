package com.luxoft.akkalabs.day2.sessions.websocket;

import javax.servlet.ServletContext;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

public class WebSocketLauncher {

    public static void launchSessionEndpoint(ServletContext context, String path, String actorSystemKey) {
        ServerContainer container = (ServerContainer) context.getAttribute("javax.websocket.server.ServerContainer");
        try {
            container.addEndpoint(ServerEndpointConfig.Builder.create(SessionEndpoint.class, path).
                    configurator(new SessionEndpointConfigurator(actorSystemKey)).build()
            );
        } catch (DeploymentException ex) {
            ex.printStackTrace();
        }
    }
}
