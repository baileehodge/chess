package WebSocketMessages.serverMessages;

import WebSocketMessages.serverMessages.ServerMessage;

import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.NOTIFICATION;

public class NotificationMessage extends ServerMessage {
    String message;

    public NotificationMessage(String text) {
        super(NOTIFICATION);
        serverMessageType = NOTIFICATION;
        message = text;

    }

    public String getMessage() {
        return message;
    }

}