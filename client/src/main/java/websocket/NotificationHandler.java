package websocket;

import WebSocketMessages.serverMessages.ErrorMessage;
import WebSocketMessages.serverMessages.LoadGameMessage;
import WebSocketMessages.serverMessages.NotificationMessage;

public interface NotificationHandler {

    void notify(NotificationMessage notification);

    void loadGame(LoadGameMessage notification);
    void error(ErrorMessage notification);
}


