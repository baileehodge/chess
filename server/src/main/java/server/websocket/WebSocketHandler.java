package server.websocket;

import WebSocketMessages.serverMessages.*;
import WebSocketMessages.userCommands.UserGameCommand;
import com.google.gson.Gson;
import org.eclipse.jetty.util.Scanner;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;

import java.io.IOException;

import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.NOTIFICATION;


public class WebSocketHandler {
    final ConnectionManager connections = new ConnectionManager();

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
    void onMessage(Session session, String message) throws IOException {
        // 1. Determine message type
        // 2. Call one of the following methods to process the message
        //    you can also call a service method, probably GameService
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(command, session);
            case JOIN_OBSERVER -> joinObserver(message);
            case MAKE_MOVE -> makeMove(message);
            case LEAVE -> leaveGame(message);
            case RESIGN -> resignGame(message);
            // set a default?
        }
    }
    // assuming these are void for now
    void joinPlayer(UserGameCommand command, Session session) throws IOException {
        // edit connections
        // tell everyone what you did

        String playerName = command.getAuthString(); // change this so that it gets the user
        connections.add(playerName, session);
        var message = String.format("%s has joined the game.", playerName);
        var notification = new ServerMessage(NOTIFICATION);
        connections.broadcast(playerName, notification);

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
