package WebSocketMessages.userCommands;

import chess.ChessMove;

import java.util.Objects;

import static WebSocketMessages.userCommands.UserGameCommand.CommandType.MAKE_MOVE;

/**
 * Represents a command a user can send the server over a websocket
 *
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class MakeMoveCommand extends UserGameCommand{
    private final Integer gameID;
    private final ChessMove move;


    public MakeMoveCommand(String authToken, Integer gameID, ChessMove move) {
        super(authToken, MAKE_MOVE);
        this.authToken = authToken;
        this.commandType = MAKE_MOVE;
        this.gameID = gameID;
        this.move = move;
    }

    public String getAuthString() {
        return super.getAuthString();
    }
    public CommandType getCommandType() {
        return super.getCommandType();
    }
    public Integer getGameID() {return this.gameID;}
    public ChessMove getMove() {return move;}

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