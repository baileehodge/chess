package WebSocketMessages.userCommands;

import java.util.Objects;

import static WebSocketMessages.userCommands.UserGameCommand.CommandType.LEAVE;

/**
 * Represents a command a user can send the server over a websocket
 *
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class LeaveCommand extends UserGameCommand{
    private final Integer gameID;


    public LeaveCommand(String authToken, Integer gameID) {
        super(authToken, LEAVE);
        this.authToken = authToken;
        this.gameID = gameID;
        this.commandType = LEAVE;
    }





    public String getAuthString() {
        return super.getAuthString();
    }
    public CommandType getCommandType() {
        return super.getCommandType();
    }
    public Integer getGameID() {return this.gameID;}


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand that))
            return false;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }
}