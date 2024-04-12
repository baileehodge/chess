package WebSocketMessages.serverMessages;

import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.ERROR;

public class ErrorMessage extends ServerMessage{
    String errorMessage;
    ServerMessageType serverMessageType;

    public ErrorMessage(String error) {
        super(ERROR);
        serverMessageType = ERROR;
        errorMessage = error;

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


}
