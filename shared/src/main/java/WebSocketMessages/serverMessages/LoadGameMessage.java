package WebSocketMessages.serverMessages;

import chess.ChessGame;

import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.*;

public class LoadGameMessage extends ServerMessage{
    ChessGame game;
    ChessGame.TeamColor playerColor;

    public LoadGameMessage(ChessGame game, ChessGame.TeamColor playerColor) {
        super(LOAD_GAME);
        serverMessageType = LOAD_GAME;
        this.game = game;
        this.playerColor = playerColor;
    }

    public LoadGameMessage(ChessGame game) {
        this(game, ChessGame.TeamColor.NONE);
    }

    public ChessGame.TeamColor getColor() {
        return playerColor;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }


}
