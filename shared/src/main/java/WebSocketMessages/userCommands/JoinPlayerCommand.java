package WebSocketMessages.userCommands;

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

    public JoinPlayerCommand(String authToken, String username, CommandType commandType, String gameID, String color, ChessMove move) {
        super(authToken);
        this.authToken = authToken;
        this.commandType = JOIN_PLAYER;
        this.gameID = gameID;
        this.color = color;
    }



    private final String authToken;
    protected CommandType commandType;
    private final String gameID;
    private final String color;


    public String getAuthString() {return authToken;}
    public CommandType getCommandType() {return this.commandType;}
    public String getGameID() {return this.gameID;}
    public String getColor() {return color;}

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