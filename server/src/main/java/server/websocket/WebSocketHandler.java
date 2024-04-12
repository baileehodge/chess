package server.websocket;

import WebSocketMessages.serverMessages.*;
import WebSocketMessages.userCommands.*;
import chess.ChessMove;
import com.google.gson.Gson;
import dataAccess.*;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.LOAD_GAME;
import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.NOTIFICATION;

import org.eclipse.jetty.websocket.api.annotations.*;

@WebSocket
public class WebSocketHandler {
    // final ConnectionManager connections = new ConnectionManager();

//    @OnWebSocketConnect
//    public void onConnect(Session session) {
//
//    }
//
//    @OnWebSocketClose
//    public void onClose(Session session) {
//
//    }

    WebSocketSessions sessions = new WebSocketSessions();
    GameDAO gameDAO = new SQLGameDAO();
    AuthDAO authDAO = new SQLAuthDAO();
    UserDAO userDAO = new SQLUserDAO();

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

        String authToken = command.getAuthString();
        int gameID = command.getGameID();
        String color = command.getColor();
        String username;
        try {
            username = authDAO.getAuth(authToken).getUsername();
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }

        sessions.addSessionToGame(gameID, authToken, session);
        // call game service method??

        String notificationText = String.format("%s has joined the game as the %s team player.", username, color);
        var notification = new ServerMessage(NOTIFICATION);
        notification.setServerMessageText(notificationText);
        String json = new Gson().toJson(notification);
        this.broadcastMessage(gameID, json, authToken);

        notification = new ServerMessage(LOAD_GAME);
        notification.setServerMessageText("Loading game " + gameID);
        json = new Gson().toJson(notification);

    }
    void joinObserver(String message, Session session) throws IOException {
        JoinPlayerCommand command = new Gson().fromJson(message, JoinPlayerCommand.class);
        // TODO

        String authToken = command.getAuthString();
        String username;
        try {
            username = authDAO.getAuth(authToken).getUsername();
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
        int gameID = command.getGameID();
        String color = command.getColor();

        sessions.addSessionToGame(gameID, authToken, session);

        String notificationText = String.format("%s has joined the game as an observer.", username);
        var notification = new ServerMessage(NOTIFICATION);
        notification.setServerMessageText(notificationText);
        String json = new Gson().toJson(notification);
        this.broadcastMessage(gameID, json, authToken);

        notification = new ServerMessage(LOAD_GAME);
        notification.setServerMessageText("Loading game " + gameID);
        json = new Gson().toJson(notification);
    }
    void makeMove(String message, Session session) throws IOException {
        MakeMoveCommand command = new Gson().fromJson(message, MakeMoveCommand.class);


        ChessMove move = command.getMove();
        String playerName = ""; // get the username from the auth

        // call gameService to make a move?

// TODO: un-comment this and fix it
//        String notificationText = String.format("%s made the following move: .", username, move.toString());
//        var notification = new ServerMessage(NOTIFICATION);
//        notification.setServerMessageText(notificationText);
//        String json = new Gson().toJson(notification);
//        this.broadcastMessage(gameID, json, authToken);
    }
    void leaveGame(String message, Session session) throws IOException {
        MakeMoveCommand command = new Gson().fromJson(message, MakeMoveCommand.class);

        // String playerName = command.getUsername();
        String playerName = ""; // get the username from the auth

// TODO: un-comment this and fix it
//
//        connections.remove(playerName);
//        // call game service method?
//
//        var notificationText = String.format("%s left the game.", playerName);
//        var notification = new ServerMessage(NOTIFICATION);
//        notification.setServerMessageText(message);
//        connections.broadcast(playerName, notification);
    }
    void resignGame(String message, Session session) throws IOException {
        MakeMoveCommand command = new Gson().fromJson(message, MakeMoveCommand.class);

        // String playerName = command.getUsername();
        String playerName = ""; // get the username from the auth

// TODO: un-comment this and fix it
//        connections.remove(playerName);
//        // call game service method?
//
//        var notificationText = String.format("%s resigned from the game.", playerName);
//        var notification = new ServerMessage(NOTIFICATION);
//        notification.setServerMessageText(notificationText);
//        connections.broadcast(playerName, notification);

    }

    void sendMessage(int gameID, String message, String authToken) {

    }
    void broadcastMessage(int gameID, String message, String notThisAuthToken) throws IOException {
        HashMap<String, Session> theSessions = sessions.getSessions(gameID);
        Session exceptThisSession = theSessions.get(notThisAuthToken);
        for (Map.Entry<String, Session> entry: theSessions.entrySet()) {
            String authToken = entry.getKey();
            Session c = entry.getValue();
            if (c.isOpen()) {
                if (!c.equals(exceptThisSession)) {
                    c.getRemote().sendString(message);
                }
            }
        }
    }

}
