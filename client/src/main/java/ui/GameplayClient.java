package ui;

import chess.ChessBoard;

import java.util.Arrays;

public class GameplayClient {

    static ServerFacade serverFacade;

    public static int getGameID() {
        return gameID;
    }

    public static void setGameID(int gameID) {
        GameplayClient.gameID = gameID;
    }

    static int gameID;
    public GameplayClient(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);
    }

    public static String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "quit" -> "Goodbye";

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