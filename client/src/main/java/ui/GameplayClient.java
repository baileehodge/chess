package ui;

import chess.ChessBoard;

public class GameplayClient {
    static ServerFacade serverFacade;
    public GameplayClient(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);
    }

    public static String eval(String line) {
        // just draws a couple of empty boards
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        DrawBoard.run(board);
        return "\n";
    }
}
