package WebSocketMessages.userCommands;

import chess.ChessMove;

import java.util.Objects;

import static WebSocketMessages.userCommands.UserGameCommand.CommandType.JOIN_OBSERVER;

/**
 * Represents a command a user can send the server over a websocket
 *
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class JoinObserverCommand extends UserGameCommand{
    private final int gameID;


    public JoinObserverCommand(String authToken, int gameID) {
        super(authToken, JOIN_OBSERVER);
        this.authToken = authToken;
        this.commandType = JOIN_OBSERVER;
        this.gameID = gameID;

    }

    public String getAuthString() {return authToken;}
    public CommandType getCommandType() {return this.commandType;}
    public int getGameID() {return this.gameID;}
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