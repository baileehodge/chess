package server.websocket;

import WebSocketMessages.serverMessages.LoadGameMessage;
import WebSocketMessages.serverMessages.NotificationMessage;
import WebSocketMessages.serverMessages.ServerMessage;
import WebSocketMessages.userCommands.*;
import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import service.ServiceException;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static service.AuthService.getAuth;
import static service.GameService.verifyGame;
import static service.GameService.*;


@WebSocket
public class WebSocketHandler {
    //

    ConnectionManager connections = new ConnectionManager();
    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);

        switch (command.commandType) {
            case JOIN_PLAYER -> joinPlayer(new Gson().fromJson(message, JoinPlayerCommand.class), session);
            case JOIN_OBSERVER -> joinObserver(new Gson().fromJson(message, JoinObserverCommand.class), session);
            case MAKE_MOVE -> makeMove(new Gson().fromJson(message, MakeMoveCommand.class), session);
            case LEAVE -> leave(new Gson().fromJson(message, LeaveCommand.class), session);
            case RESIGN -> resign(new Gson().fromJson(message, ResignCommand.class), session);
        }


    }

    // I am, once again, assuming these bad boys are void until proven otherwise
    void joinPlayer(JoinPlayerCommand command, Session session) throws ServiceException, DataAccessException, IOException {
        // gets the username based on the auth token
        String playerName = getAuth(command.getAuthString()).getUsername();
        int gameID = command.getGameID();
        ChessGame game = getGame(gameID);
        ChessGame.TeamColor color = command.getColor();

        // verify game
        // if (!gameVerification(session, gameID, command.getAuthString())) return;

        connections.add(playerName, session, gameID);
        connections.gossip(playerName, new NotificationMessage(playerName + " just joined the game."));

        LoadGameMessage loadGameMessage = new LoadGameMessage(game, color);
        session.getRemote().sendString(new Gson().toJson(loadGameMessage));
    }

    void joinObserver(JoinObserverCommand command, Session session) throws DataAccessException, ServiceException, IOException {
        // gets the username based on the auth token
        String playerName = getAuth(command.getAuthString()).getUsername();
        int gameID = command.getGameID();
        ChessGame game = getGame(gameID);

        // verify game
        // if (!gameVerification(session, gameID, command.getAuthString())) return;

        connections.add(playerName, session, gameID);
        connections.gossip(playerName, new NotificationMessage(playerName + " just joined the game as an observer."));

        LoadGameMessage loadGameMessage = new LoadGameMessage(game);
        session.getRemote().sendString(new Gson().toJson(loadGameMessage));
    }

    private boolean gameVerification(Session session, int gameID, String authString) throws DataAccessException, IOException, ServiceException {
        if(!verifyGame(gameID))
        {
            Error messageToSend = new Error("Error: That game doesn't exist.");
            session.getRemote().sendString(new Gson().toJson(messageToSend));
            return false;
        }
        // verify auth
        else if(getAuth(authString) == null)
        {
            Error messageToSend = new Error("Error: That authToken is not valid.");
            session.getRemote().sendString(new Gson().toJson(messageToSend));
            return false;
        }
        return true;
    }

    void makeMove(MakeMoveCommand command, Session session) {
        // TODO

    }

    void leave(LeaveCommand command, Session session) {
        // TODO

    }

    void resign(ResignCommand command, Session session) {
        // TODO

    }

    @OnWebSocketError
    public void onError(Throwable throwable) {
        // TODO: make this useful?
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        System.out.println("Threw an @OnWebSocketError");
        System.out.println(throwable.getMessage());
    }





}
