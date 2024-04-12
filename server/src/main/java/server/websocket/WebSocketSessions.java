package server.websocket;

import org.eclipse.jetty.websocket.api.Session;

import java.util.HashMap;
import java.util.Map;

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
        // finesse it so that we can edit the thing
        HashMap<String, Session> token = sessionMap.get(gameID);
        sessionMap.remove(gameID);
        token.remove(authToken, session);
        sessionMap.put(gameID, token);
    }

    public void removeSessions(Session session) {
        // remove the session and everything in it
        int gameID = 0;
        String authToken = null;
        for (Map.Entry<Integer, HashMap<String, Session>> masterMap : sessionMap.entrySet()){
            for (Map.Entry<String, Session> tokenToSession : masterMap.getValue().entrySet()){
                if(tokenToSession.getValue().equals(session)){
                    gameID = masterMap.getKey();
                    authToken = tokenToSession.getKey();
                    break;
                }
            }
            if(gameID != 0) break;
        }
        if(gameID != 0) removeSessionFromGame(gameID, authToken, session);
    }

    public HashMap<String, Session> getSessions(int gameID) {
        return sessionMap.get(gameID);
    }

    public String getAuth(Session session){
        System.out.println(sessionMap);
        for (HashMap<String, Session> token : sessionMap.values()){
            for (Map.Entry<String, Session> entry : token.entrySet()){
                if(entry.getValue() == session) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }
}
