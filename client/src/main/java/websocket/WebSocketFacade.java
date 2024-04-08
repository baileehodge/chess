package websocket;
import com.mysql.cj.Session;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;


public class WebSocketFacade extends Endpoint {
    Session session;

    void connect() {

    }
    void disconnect() {

    }

    // outgoing messages
    // 1. create command message
    // 2. send message to server

    void joinPlayer() {

    }
    void joinObserver() {

    }
    void makeMove() {

    }
    void leaveGame() {

    }
    void resignGame() {

    }


    void sendMessage() {

    }


    // process incoming messages
    // 1. deserialize message
    // 2. call gameHandler to process message

    void onMessage(String message) {

    }

    @Override
    public void onOpen(javax.websocket.Session session, EndpointConfig endpointConfig) {

    }
}
