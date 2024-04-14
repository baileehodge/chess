package ui;

import WebSocketMessages.ResponseException;
import chess.ChessBoard;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.util.Arrays;

public class GameplayClient {

    static ServerFacade serverFacade;
    static int gameID;
    public String serverURL;
    public WebSocketFacade ws;
    NotificationHandler notificationHandler;



    public GameplayClient(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);
        this.serverURL = serverUrl;
    }

    public static int getGameID() {
        return gameID;
    }

    public static void setGameID(int gameID) {
        GameplayClient.gameID = gameID;
    }




    public String eval(String input) throws ResponseException {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            // redundant: case "help" -> help();
            case "redrawBoard" -> redrawBoard();
            case "leave" -> leave(params);
            case "move" -> move(params);
            case "resign" -> resign();
            case "highlight" -> highlight();
            default -> help();
        };
    }



    private static String help() {
        return """
                    - help
                    - redrawBoard
                    - move <move> (Format: 3,4:4,5)
                    - highlight <piece> (Format: 3,4 . Highlights legal moves for that piece.)
                    - leave
                    - resign
                    """;
    }

    private String redrawBoard() {
        return "Redraws the chess board upon the user’s request.\n";
    }

    private String move(String... params) {
        return "Allow the user to input what move they want to make. The board is updated to reflect the result of the move, and the board automatically updates on all clients involved in the game.\n";
    }

    private String leave(String... params) throws ResponseException {
        String authToken = "fake auth";
        // TODO: figure out how to get this auth token

        ws = new WebSocketFacade(serverURL, notificationHandler);
        ws.leave(authToken, gameID);

        Repl.setState(Repl.State.SIGNEDIN);

        return "You left the game\n";
    }


    private String resign() {
        return "Prompts the user to confirm they want to resign. If they do, the user forfeits the game and the game is over. Does not cause the user to leave the game.\n";
    }

    private String highlight() {
        return "Allows the user to input what piece for which they want to highlight legal moves. The selected piece’s current square and all squares it can legally move to are highlighted. This is a local operation and has no effect on remote users’ screens.\n";
    }




    // Draws a basic starting board
    // probably obsolete for phase 6
    private static String drawBasic() {
        // draws a starting board
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        DrawBoard.run(board, null);
        return "\n";
    }
}