package server.websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.util.HashMap;

public class WebSocketSessions {
    // see Prof Rodham's diagram
    HashMap<Integer, HashMap<String, Session>> sessionMap = new HashMap<>();

    public void addSessionToGame(int gameID, String authToken, Session session) {
        HashMap<String, Session> token = sessionMap.get(gameID);
        if(token == null){
            token = new HashMap<>();
        }
        token.put(authToken, session);
        sessionMap.put(gameID, token);
    }
    public void removeSessionFromGame(int gameID, String authToken, Session session) {
        HashMap<String, Session> token = sessionMap.get(gameID);
        sessionMap.remove(gameID);
        token.remove(authToken, session);
        sessionMap.put(gameID, token);

    }

    public void removeSessions() {
        // remove the session and everything in it

    }


    public HashMap<String, Session> getSessions(int gameID) {
        return sessionMap.get(gameID);
    }
}
