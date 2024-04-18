package WebSocketMessages.userCommands;

import java.util.Objects;

import static WebSocketMessages.userCommands.UserGameCommand.CommandType.LEAVE;

public class LeaveCommand extends UserGameCommand{
    private final Integer gameID;

    public String getAuthString() {
        return super.getAuthString();
    }
    public Integer getGameID() {return this.gameID;}
    public CommandType getCommandType() {
        return super.getCommandType();
    }

    public LeaveCommand(String authToken, Integer gameID) {
        super(authToken, LEAVE);
        this.authToken = authToken;
        this.gameID = gameID;
        this.commandType = LEAVE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand that))
            return false;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }
}