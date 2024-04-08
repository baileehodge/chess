package server.websocket;

import com.mysql.cj.Session;

import java.util.Map;

public class WebSocketSessions {
    Map<Integer, Map<String, Session>> sessionMap;

    void addSessionToGame(int gameID, String authToken, Session session) {

    }

    void removeSessionFromGame(int gameID,String authToken, Session session) {

    }

    void removeSession(Session session) {

    }

}
