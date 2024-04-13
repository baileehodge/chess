package ui;

import chess.ChessBoard;

import java.util.Arrays;

public class GameplayClient {

    static ServerFacade serverFacade;

    public GameplayClient(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);
    }

    public static int getGameID() {
        return gameID;
    }

    public static void setGameID(int gameID) {
        GameplayClient.gameID = gameID;
    }

    static int gameID;


    public static String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "help" -> "Displays text informing the user what actions they can take.\n";
            case "redrawBoard" -> "Redraws the chess board upon the user’s request.\n";
            case "leave" -> "Removes the user from the game (whether they are playing or observing the game). The client transitions back to the Post-Login UI.\n";
            case "move" -> "Allow the user to input what move they want to make. The board is updated to reflect the result of the move, and the board automatically updates on all clients involved in the game.\n";
            case "resign" -> "Prompts the user to confirm they want to resign. If they do, the user forfeits the game and the game is over. Does not cause the user to leave the game.\n";
            case "highlight" -> "Allows the user to input what piece for which they want to highlight legal moves. The selected piece’s current square and all squares it can legally move to are highlighted. This is a local operation and has no effect on remote users’ screens.\n";
            default -> drawBasic();
        };
    }
    private static String drawBasic() {
        // draws a starting board
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        DrawBoard.run(board, null);
        return "\n";
    }
}