package websocket;

import WebSocketMessages.ResponseException;
import WebSocketMessages.serverMessages.ServerMessage;
import WebSocketMessages.userCommands.JoinObserverCommand;
import WebSocketMessages.userCommands.JoinPlayerCommand;
import WebSocketMessages.userCommands.LeaveCommand;
import WebSocketMessages.userCommands.UserGameCommand;
import chess.ChessGame;
import com.google.gson.Gson;
import ui.DrawBoard;

import javax.management.Notification;
import javax.websocket.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;


public class WebSocketFacade extends Endpoint {
    Session session;

    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage newMessage = new Gson().fromJson(message, ServerMessage.class);

                    switch(newMessage.getServerMessageType()) {
                        case NOTIFICATION: {
                            Notification messageOut = new Gson().fromJson(message, Notification.class);
                            System.out.println(messageOut.getMessage());
                        }
                        case ERROR: {
                            Error messageOut = new Gson().fromJson(message, Error.class);
                            System.out.println(messageOut.getMessage());
                        }
                        case LOAD_GAME: {
                            // does it work if I print the ChessBoard from here?
                            ChessGame game = new Gson().fromJson(message, ChessGame.class);
                            DrawBoard.run(game.getBoard(), ChessGame.TeamColor.WHITE);
                            // TODO: change this to have the player's team... somehow...
                            //
                        }
                    }
                }
            });

        } catch (URISyntaxException | DeploymentException | IOException e) {
            throw new ResponseException(500, e.getMessage());
        }
    }

    // outgoing messages
    // 1. create command message
    // 2. send message to server

    public void joinPlayer(String auth, int game, ChessGame.TeamColor color) throws ResponseException {
        try {
            // 1.
            JoinPlayerCommand newCommand = new JoinPlayerCommand(auth, game, color);

            // 2.
            this.session.getBasicRemote().sendText(new Gson().toJson(newCommand));
        }
        catch (IOException ex)
        {
            throw new ResponseException(500,ex.getMessage());
        }

    }

    public void joinObserver(String auth, int game) throws ResponseException {
        try {
            // 1.
            JoinObserverCommand newCommand = new JoinObserverCommand(auth, game);

            // 2.
            this.session.getBasicRemote().sendText(new Gson().toJson(newCommand));
        }
        catch (IOException ex)
        {
            throw new ResponseException(500,ex.getMessage());
        }

    }

    public void movePiece() {
        // TODO

    }

    public void leave(String authToken, int gameID) throws ResponseException {
        try {
            LeaveCommand newCommand = new LeaveCommand(authToken, gameID);
            this.session.getBasicRemote().sendText(new Gson().toJson(newCommand));
        }
        catch(IOException ex)
        {
            throw new ResponseException(500, ex.getMessage());
        }

    }

    public void resign() {
        // TODO

    }












    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        // https://i.kym-cdn.com/entries/icons/mobile/000/026/944/falls.jpg
        // Woah, this is worthless!
    }
}
