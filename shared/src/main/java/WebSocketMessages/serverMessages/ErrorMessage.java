package WebSocketMessages.serverMessages;

import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.ERROR;

public class ErrorMessage extends ServerMessage{
    String errorMessage;

    public ErrorMessage(String error) {
        super(ERROR);
        serverMessageType = ERROR;
        errorMessage = error;

    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
