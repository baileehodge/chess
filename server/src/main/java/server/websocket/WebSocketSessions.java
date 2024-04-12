package server.websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.util.HashMap;

public class WebSocketSessions {
    // see Prof Rodham's diagram
    HashMap<Integer, HashMap<String, Session>> sessionMap = new HashMap<>();

    public void addSessionToGame() {

    }
    public void removeSessionFromGame() {

    }

    public void removeSession() {

    }


    public HashMap<String, Session> getSessions() {
        return null;
    }
}
