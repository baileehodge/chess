package WebSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

import java.util.Objects;

import static WebSocketMessages.userCommands.UserGameCommand.CommandType.JOIN_PLAYER;

/**
 * Represents a command a user can send the server over a websocket
 *
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class JoinPlayerCommand extends UserGameCommand{

    public JoinPlayerCommand(String authToken, int gameID, ChessGame.TeamColor playerColor) {
        super(authToken, JOIN_PLAYER);
        this.authToken = authToken;
        this.commandType = JOIN_PLAYER;
        this.gameID = gameID;
        this.playerColor = playerColor;
    }


    private final int gameID;
    private final ChessGame.TeamColor playerColor;


    public String getAuthString() {return authToken;}
    public CommandType getCommandType() {return this.commandType;}
    public int getGameID() {return this.gameID;}
    public ChessGame.TeamColor getColor() {return playerColor;}


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand))
            return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }
}