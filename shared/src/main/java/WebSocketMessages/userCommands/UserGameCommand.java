package WebSocketMessages.userCommands;

import chess.ChessMove;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 *
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {

    public UserGameCommand(String authToken, String username, CommandType commandType, String gameID, String color, ChessMove move) {
        this.authToken = authToken;
        this.username = username;
        this.commandType = commandType;
        this.gameID = gameID;
        this.color = color;
        this.move = move;
    }

    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    private final String authToken;
    protected CommandType commandType;
    private final String username;
    private final String gameID;
    private final String color;
    private final ChessMove move;


    public String getAuthString() {return authToken;}
    public CommandType getCommandType() {return this.commandType;}
    public String getUsername() {return this.username;}
    public String getGameID() {return this.gameID;}
    public String getColor() {return color;}
    public ChessMove getMove() {return move;}

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