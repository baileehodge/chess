package server.websocket;

import com.mysql.cj.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;

import java.util.Map;

public class WebSocketHandler {
    final WebSocketSessions sessions;

    public WebSocketHandler(WebSocketSessions sessions) {
        this.sessions = sessions;
    }

    @OnWebSocketConnect
    void onConnect(Session session) {

    }

    @OnWebSocketClose
    void onClose(Session session) {

    }

    @OnWebSocketError
    void onError(Throwable throwable) {

    }

    @OnWebSocketMessage
    void onMessage(Session session, String str) {
        // 1. Determine message type
        // 2. Call one of the following methods to process the message
        //    you can also call a service method, probably GameService
    }
    // assuming these are void for now
    void joinPlayer(String message) {

    }
    void joinObserver(String message) {

    }
    void makeMove(String message) {

    }
    void leaveGame(String message) {

    }
    void resignGame(String message) {

    }

    void sendMessage(int gameID, String message, String authToken) {

    }
    void broadcastMessage(int gameID, String message, String notThisAuthToken) {

    }

}
