package websocket;

import WebSocketMessages.serverMessages.ErrorMessage;
import WebSocketMessages.serverMessages.LoadGameMessage;
import WebSocketMessages.serverMessages.NotificationMessage;
import WebSocketMessages.serverMessages.ServerMessage;

import javax.management.Notification;

public interface NotificationHandler {

    void notify(NotificationMessage notification);

    void loadGame(LoadGameMessage notification);
    void error(ErrorMessage notification);
}
