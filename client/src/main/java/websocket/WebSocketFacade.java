package websocket;
import WebSocketMessages.ResponseException;
import WebSocketMessages.userCommands.JoinPlayerCommand;
import com.google.gson.Gson;

import javax.management.Notification;
import javax.websocket.*;
import javax.websocket.MessageHandler;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;




public class WebSocketFacade extends Endpoint {
    Session session;
    NotificationHandler notificationHandler;

    public WebSocketFacade(String url, NotificationHandler notificationHandler) throws ResponseException {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/connect");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);
            this.session.getBasicRemote().sendText("there ain't a cloud in sight"); // client send
            System.out.println("CLIENT SEND (probably)");

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) { // client receive
                    System.out.println(message);
                    System.out.println("CLIENT RECEIVE");
                    Notification notification = new Gson().fromJson(message, Notification.class);
                    notificationHandler.notify(notification);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }

    void connect() {

    }
    void disconnect() {

    }

    // outgoing messages
    // 1. create command message
    // 2. send message to server

    public void joinPlayer(String authToken, int gameID, String color) throws ResponseException {
        try {
            var action = new JoinPlayerCommand(authToken, gameID, color);
            this.session.getBasicRemote().sendText(new Gson().toJson(action)); // client send
            System.out.println("CLIENT SEND");
        } catch (IOException ex) {
            throw new ResponseException(500, ex.getMessage());
        }
    }
    public void joinObserver() {

    }
    public void makeMove() {

    }
    public void leaveGame() {

    }
    public void resignGame() {

    }


    void sendMessage() {

    }


    // process incoming messages
    // 1. deserialize message
    // 2. call gameHandler to process message

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        // Handle the WebSocket connection opening
        System.out.println("WebSocket connection opened");
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        // Handle the WebSocket connection closing
        System.out.println("WebSocket connection closed: " + closeReason.getReasonPhrase());
    }
    @OnError
    public void onError(Session session, Throwable throwable) {
        // Handle any errors that occur during the WebSocket connection
        System.out.println("WebSocket error: " + throwable.getMessage());
    }
}
