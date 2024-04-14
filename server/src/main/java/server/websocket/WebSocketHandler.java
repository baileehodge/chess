package server.websocket;

import WebSocketMessages.userCommands.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import com.google.gson.Gson;


import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static dataAccess.DatabaseManager.getConnection;

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
    void joinPlayer(JoinPlayerCommand command, Session session) {

    }

    void joinObserver(JoinObserverCommand command, Session session) {

    }

    void makeMove(MakeMoveCommand command, Session session) {

    }

    void leave(LeaveCommand command, Session session) {

    }

    void resign(ResignCommand command, Session session) {

    }

    @OnWebSocketError
    public void onError(Throwable throwable) {
        // TODO: make this useful?
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        System.out.println("Threw an @OnWebSocketError");
        System.out.println(throwable.getMessage());
    }





}
