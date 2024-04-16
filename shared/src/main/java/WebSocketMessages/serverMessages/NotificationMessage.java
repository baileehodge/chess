package WebSocketMessages.serverMessages;

import WebSocketMessages.serverMessages.ServerMessage;

import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.ERROR;
import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.NOTIFICATION;

public class NotificationMessage extends ServerMessage {

    public NotificationMessage(String text) {
        super(NOTIFICATION);
        serverMessageType = NOTIFICATION;
        serverMessageText = text;

    }

    public String getMessage() {
        return serverMessageText;
    }

}