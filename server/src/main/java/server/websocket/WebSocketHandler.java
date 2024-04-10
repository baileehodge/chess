package server.websocket;

import WebSocketMessages.serverMessages.*;
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
            case JOIN_PLAYER -> joinPlayer(command, session);
            case JOIN_OBSERVER -> joinObserver(command, session);
            case MAKE_MOVE -> makeMove(command, session);
            case LEAVE -> leaveGame(command, session);
            case RESIGN -> resignGame(command, session);
            // set a default?
        }
    }
    // assuming these are void for now
    void joinPlayer(UserGameCommand command, Session session) throws IOException {
        String playerName = command.getUsername();
        String color = command.getColor();

        connections.add(playerName, session);
        // call game service method?

        String message = String.format("%s has joined the game as the %s team player.", playerName, color);
        var notification = new ServerMessage(NOTIFICATION);
        notification.setServerMessageText(message);
        connections.broadcast(playerName, notification);

    }
    void joinObserver(UserGameCommand command, Session session) throws IOException {
        String playerName = command.getUsername();

        connections.add(playerName, session);
        // call game service method?

        var message = String.format("%s has joined the game as an observer.", playerName);
        var notification = new ServerMessage(NOTIFICATION);
        notification.setServerMessageText(message);
        connections.broadcast(playerName, notification);
    }
    void makeMove(UserGameCommand command, Session session) throws IOException {
        ChessMove move = command.getMove();
        String playerName = command.getUsername();

        // call gameService to make a move?

        var message = String.format("%s made the following move: %s.", playerName, move.toString());
        var notification = new ServerMessage(NOTIFICATION);
        notification.setServerMessageText(message);
        connections.broadcast(playerName, notification);
    }
    void leaveGame(UserGameCommand command, Session session) throws IOException {
        String playerName = command.getUsername();

        connections.remove(playerName);
        // call game service method?

        var message = String.format("%s left the game.", playerName);
        var notification = new ServerMessage(NOTIFICATION);
        notification.setServerMessageText(message);
        connections.broadcast(playerName, notification);
    }
    void resignGame(UserGameCommand command, Session session) throws IOException {
        String playerName = command.getUsername();

        connections.remove(playerName);
        // call game service method?

        var message = String.format("%s resigned from the game.", playerName);
        var notification = new ServerMessage(NOTIFICATION);
        notification.setServerMessageText(message);
        connections.broadcast(playerName, notification);

    }

    void sendMessage(int gameID, String message, String authToken) {

    }
    void broadcastMessage(int gameID, String message, String notThisAuthToken) {

    }

}
