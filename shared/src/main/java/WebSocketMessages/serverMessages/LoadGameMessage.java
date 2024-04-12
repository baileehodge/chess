package WebSocketMessages.serverMessages;

import chess.ChessGame;

import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.*;

public class LoadGameMessage extends ServerMessage{
    ChessGame game;
    ServerMessageType serverMessageType;

    public LoadGameMessage(ChessGame game) {
        super(ERROR);
        serverMessageType = LOAD_GAME;
        this.game = game;

    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }


}
