package server.websocket;

import WebSocketMessages.serverMessages.LoadGameMessage;
import WebSocketMessages.serverMessages.NotificationMessage;
import WebSocketMessages.serverMessages.ServerMessage;
import WebSocketMessages.userCommands.*;
import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.GameData;
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
    private static GameDAO gameAccess;
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
        String playerName = getAuth(command.getAuthString()).getUsername();
        int gameID = command.getGameID();
        ChessGame game = getGame(gameID);
        ChessGame.TeamColor color = command.getColor();

        // verify game?

        connections.add(playerName, session, gameID);
        connections.gossip(playerName, new NotificationMessage(playerName + " just joined the game as the " + color + " team."));

        LoadGameMessage loadGameMessage = new LoadGameMessage(game, color);
        session.getRemote().sendString(new Gson().toJson(loadGameMessage));
    }

    void joinObserver(JoinObserverCommand command, Session session) throws DataAccessException, ServiceException, IOException {
        String playerName = getAuth(command.getAuthString()).getUsername();
        int gameID = command.getGameID();
        ChessGame game = getGame(gameID);

        // verify game?

        connections.add(playerName, session, gameID);
        connections.gossip(playerName, new NotificationMessage(playerName + " just joined the game as an observer."));

        LoadGameMessage loadGameMessage = new LoadGameMessage(game);
        session.getRemote().sendString(new Gson().toJson(loadGameMessage));
    }

    // see CodeQualityVictims.txt for gameVerification function

    void makeMove(MakeMoveCommand command, Session session) throws DataAccessException, InvalidMoveException, ServiceException, IOException {
        ChessGame game = getGame(command.getGameID());
        ChessMove move = command.getMove();

        // move
        game.makeMove(move);

        // update db
        GameData gameData = gameAccess.getGame(command.getGameID());
        gameData.setGame(game);
        gameAccess.updateGame(gameData);

        // notify
        String playerName = getAuth(command.getAuthString()).getUsername();
        connections.gossip(playerName, new NotificationMessage(playerName + " made the following move: " + move.toString() +"\n"));

        NotificationMessage notificationMessage = new NotificationMessage("You made the following move: " + move.toString() +"\n");
        session.getRemote().sendString(new Gson().toJson(notificationMessage));
    }

    void leave(LeaveCommand command, Session session) throws ServiceException, DataAccessException, IOException {
        // gets the username based on the auth token
        String playerName = getAuth(command.getAuthString()).getUsername();

        connections.remove(playerName);
        connections.gossip(playerName, new NotificationMessage(playerName + " left the game."));

        NotificationMessage notificationMessage = new NotificationMessage(playerName + " left the game.");
        session.getRemote().sendString(new Gson().toJson(notificationMessage));
    }

    void resign(ResignCommand command, Session session) throws ServiceException, IOException, DataAccessException {
        String playerName = getAuth(command.getAuthString()).getUsername();

        LeaveCommand leave = new LeaveCommand(command.getAuthString(), command.getGameID());
        JoinObserverCommand join = new JoinObserverCommand(command.getAuthString(), command.getGameID());
        leave(leave, session);
        joinObserver(join,session);

        connections.gossip(playerName, new NotificationMessage(playerName + " resigned from the game."));

    }

    @OnWebSocketError
    public void onError(Throwable throwable) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        System.out.println("Threw an @OnWebSocketError");
        System.out.println(throwable.getMessage());
    }





}
