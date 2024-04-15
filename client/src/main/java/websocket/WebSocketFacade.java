package websocket;

import WebSocketMessages.ResponseException;
import WebSocketMessages.serverMessages.LoadGameMessage;
import WebSocketMessages.serverMessages.ServerMessage;
import WebSocketMessages.userCommands.*;
import chess.*;
import com.google.gson.Gson;

import javax.management.Notification;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static ui.DrawBoard.drawBoard;


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
                            LoadGameMessage gameMessage = new Gson().fromJson(message, LoadGameMessage.class);
                            ChessBoard gameBoard = gameMessage.getGame().getBoard();
                            ChessGame.TeamColor color = gameMessage.getColor();
                            drawBoard(gameBoard, color);
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

    public void joinPlayer(String auth, int gameID, ChessGame.TeamColor color) throws ResponseException {
        try {
            // 1.
            JoinPlayerCommand newCommand = new JoinPlayerCommand(auth, gameID, color);

            // 2.
            this.session.getBasicRemote().sendText(new Gson().toJson(newCommand));
        }
        catch (IOException ex)
        {
            throw new ResponseException(500,ex.getMessage());
        }

    }

    public void joinObserver(String auth, int gameID) throws ResponseException {
        try {
            // 1.
            JoinObserverCommand newCommand = new JoinObserverCommand(auth, gameID);

            // 2.
            this.session.getBasicRemote().sendText(new Gson().toJson(newCommand));
        }
        catch (IOException ex)
        {
            throw new ResponseException(500,ex.getMessage());
        }

    }

    public void movePiece(String authToken, int gameID, ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionType) throws ResponseException {
        try {
            // 1.
            // MakeMoveCommand wants an auth, a gameID, and a ChessMove
            MakeMoveCommand newCommand = new MakeMoveCommand(authToken, gameID, new ChessMove(startPosition, endPosition, promotionType));
            // 2.
            this.session.getBasicRemote().sendText(new Gson().toJson(newCommand));
        }
        catch(IOException ex)
        {
            throw new ResponseException(500, ex.getMessage());
        }

    }

    public void leave(String authToken, int gameID) throws ResponseException {
        try {
            // 1.
            LeaveCommand newCommand = new LeaveCommand(authToken, gameID);
            // 2.
            this.session.getBasicRemote().sendText(new Gson().toJson(newCommand));
        }
        catch(IOException ex)
        {
            throw new ResponseException(500, ex.getMessage());
        }

    }

    public void resign(String authToken, int gameID) throws ResponseException {
        try {
            // 1.
            ResignCommand newCommand = new ResignCommand(authToken, gameID);
            // 2.
            this.session.getBasicRemote().sendText(new Gson().toJson(newCommand));
        }
        catch(IOException ex)
        {
            throw new ResponseException(500, ex.getMessage());
        }

    }












    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        // https://i.kym-cdn.com/entries/icons/mobile/000/026/944/falls.jpg
        // Woah, this is worthless!
    }
}
