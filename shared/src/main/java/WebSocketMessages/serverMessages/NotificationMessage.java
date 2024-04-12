package WebSocketMessages.serverMessages;

import WebSocketMessages.serverMessages.ServerMessage;

import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.ERROR;
import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.NOTIFICATION;

public class NotificationMessage extends ServerMessage {
    String message;
    ServerMessageType serverMessageType;

    public NotificationMessage(String error) {
        super(ERROR);
        serverMessageType = NOTIFICATION;
        message = error;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String errorMessage) {
        this.message = errorMessage;
    }


}