package WebSocketMessages.serverMessages;

import chess.ChessGame;

import static WebSocketMessages.serverMessages.ServerMessage.ServerMessageType.*;

public class LoadGameMessage extends ServerMessage{
    ChessGame game;
    ChessGame.TeamColor teamColor;

    public LoadGameMessage(ChessGame game, ChessGame.TeamColor color) {
        super(LOAD_GAME);
        serverMessageType = LOAD_GAME;
        this.game = game;
        teamColor = color;
    }

    public LoadGameMessage(ChessGame game) {
        this(game, ChessGame.TeamColor.NONE);
    }

    public ChessGame.TeamColor getColor() {
        return teamColor;
    }

    public void setColor(ChessGame.TeamColor currentUser) {
        this.teamColor = currentUser;
    }

    public ChessGame getGame() {
        return game;
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }


}
