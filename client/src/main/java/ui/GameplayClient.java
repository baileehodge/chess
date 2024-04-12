package ui;

import WebSocketMessages.ResponseException;
import chess.ChessBoard;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.util.Arrays;

import static ui.Repl.getToken;

public class GameplayClient {
    static WebSocketFacade wsServer;
    static ServerFacade serverFacade;

    static ServerFacade httpServer;
    private final String serverUrl;

    private static int gameID = 0;

    private final NotificationHandler notificationHandler;
    public GameplayClient(String serverUrl, NotificationHandler notificationHandler) throws ResponseException {
        this.notificationHandler = notificationHandler;
        this.serverUrl = serverUrl;
        wsServer = new WebSocketFacade(serverUrl, notificationHandler);
        httpServer = new ServerFacade(serverUrl);
    }

    public static int getGameID() {
        return gameID;
    }

    public static void setGameID(int gameID) {
        GameplayClient.gameID = gameID;
    }

    public static String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "quit" -> "Goodbye";
            case "redrawBoard" -> redrawBoard();
            case "showLegalMoves" -> showLegalMoves(params);
            case "makemove" -> makeMove(params);
            case "leave" -> leave();
            case "resign" -> resign();
            case "help" -> help();
            default -> help();
        };
    }

    private static String showLegalMoves(String[] params) {
        return "not yet implemented";
    }

    private static String redrawBoard() {
        return "not yet implemented";
    }

    private static String joinPlayer(String... params) {
        // TODO: I don't... I don't even need this... do I?
        try {
            System.out.println("before joinplayer() from the client");

            String auth = getToken();
            String color = params[0];


            wsServer.joinPlayer(auth, gameID, color);


            System.out.println("after joinplayer() from the client");

            return "this is the joinplayer return statement";
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    private static String joinObserver() {
        return "not yet implemented";
    }

    private static String makeMove(String... params) {
        return "not yet implemented";
    }

    private static String leave() {
        return "not yet implemented";
    }

    private static String resign() {
        return "not yet implemented";
    }


    private static String help() {
        return """
                    - redrawBoard
                    - showLegalMoves
                    - makeMove <gameID> <move, format 1:5-1:6>
                    - leave <gameID>
                    - resign <gameID>
                    - help
                    """;
    }



    private static String drawBasic() {
        // draws a starting board
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        DrawBoard.run(board);
        return "\n";
    }

    // this is probably a useless method

    private static String drawBoard(ChessBoard board) {
        DrawBoard.run(board);
        return "\n";
    }
}
