package WebSocketMessages.serverMessages;

import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.ERROR;

public class ErrorMessage extends ServerMessage{

    public ErrorMessage(String error) {
        super(ERROR);
        serverMessageType = ERROR;
        serverMessageText = error;

    }

    public String getErrorMessage() {
        return serverMessageText;
    }

    public void setErrorMessage(String errorMessage) {
        this.serverMessageText = errorMessage;
    }


}
