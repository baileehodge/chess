package server.websocket;

import WebSocketMessages.serverMessages.*;
import WebSocketMessages.userCommands.JoinObserverCommand;
import WebSocketMessages.userCommands.JoinPlayerCommand;
import WebSocketMessages.userCommands.MakeMoveCommand;
import WebSocketMessages.userCommands.UserGameCommand;
import chess.ChessMove;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.NOTIFICATION;

import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class WebSocketHandler {
    final ConnectionManager connections = new ConnectionManager();

//    @OnWebSocketConnect
//    public void onConnect(Session session) {
//
//    }
//
//    @OnWebSocketClose
//    public void onClose(Session session) {
//
//    }

    @OnWebSocketError
    public void onError(Throwable throwable) {

    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws IOException {
        // 1. Determine message type
        // 2. Call one of the following methods to process the message
        //    you can also call a service method, probably GameService
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        switch (command.getCommandType()) {
            case JOIN_PLAYER -> joinPlayer(message, session);
            case JOIN_OBSERVER -> joinObserver(message, session);
            case MAKE_MOVE -> makeMove(message, session);
            case LEAVE -> leaveGame(message, session);
            case RESIGN -> resignGame(message, session);
            // set a default?
        }
    }
    // assuming these are void for now
    void joinPlayer(String message, Session session) throws IOException {
        JoinPlayerCommand command = new Gson().fromJson(message, JoinPlayerCommand.class);

        String auth = command.getAuthString();
        String color = command.getColor();
        String playerName = ""; // get the username from the auth

        connections.add(playerName, session);
        // call game service method??

        String notificationText = String.format("%s has joined the game as the %s team player.", playerName, color);
        var notification = new ServerMessage(NOTIFICATION);
        notification.setServerMessageText(notificationText);
        connections.broadcast(playerName, notification);

    }
    void joinObserver(String message, Session session) throws IOException {
        JoinObserverCommand command = new Gson().fromJson(message, JoinObserverCommand.class);


        //String playerName = command.getUsername();
        String playerName = ""; // get the username from the auth


        connections.add(playerName, session);
        // call game service method?

        var notificationText = String.format("%s has joined the game as an observer.", playerName);
        var notification = new ServerMessage(NOTIFICATION);
        notification.setServerMessageText(notificationText);
        connections.broadcast(playerName, notification);
    }
    void makeMove(String message, Session session) throws IOException {
        MakeMoveCommand command = new Gson().fromJson(message, MakeMoveCommand.class);


        ChessMove move = command.getMove();
        String playerName = ""; // get the username from the auth

        // call gameService to make a move?

        var notificationText = String.format("%s made the following move: %s.", playerName, move.toString());
        var notification = new ServerMessage(NOTIFICATION);
        notification.setServerMessageText(notificationText);
        connections.broadcast(playerName, notification);
    }
    void leaveGame(String message, Session session) throws IOException {
        MakeMoveCommand command = new Gson().fromJson(message, MakeMoveCommand.class);

        // String playerName = command.getUsername();
        String playerName = ""; // get the username from the auth


        connections.remove(playerName);
        // call game service method?

        var notificationText = String.format("%s left the game.", playerName);
        var notification = new ServerMessage(NOTIFICATION);
        notification.setServerMessageText(message);
        connections.broadcast(playerName, notification);
    }
    void resignGame(String message, Session session) throws IOException {
        MakeMoveCommand command = new Gson().fromJson(message, MakeMoveCommand.class);

        // String playerName = command.getUsername();
        String playerName = ""; // get the username from the auth


        connections.remove(playerName);
        // call game service method?

        var notificationText = String.format("%s resigned from the game.", playerName);
        var notification = new ServerMessage(NOTIFICATION);
        notification.setServerMessageText(notificationText);
        connections.broadcast(playerName, notification);

    }

    void sendMessage(int gameID, String message, String authToken) {

    }
    void broadcastMessage(int gameID, String message, String notThisAuthToken) {

    }

}
