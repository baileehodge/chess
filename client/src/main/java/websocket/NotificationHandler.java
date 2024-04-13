package websocket;

import WebSocketMessages.serverMessages.ServerMessage;

import javax.management.Notification;

public interface NotificationHandler {

    void notify(ServerMessage notification);
}
