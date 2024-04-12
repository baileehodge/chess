package ui;

import WebSocketMessages.ResponseException;
import chess.ChessBoard;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import javax.management.Notification;
import java.io.IOException;
import java.util.Arrays;

import static ui.Repl.getToken;

public class GameplayClient {
    static WebSocketFacade wsServer;
    static ServerFacade serverFacade;

    static ServerFacade httpServer;
    private final String serverUrl;
    private static int gameID;

    private final NotificationHandler notificationHandler;
    public GameplayClient(String serverUrl, NotificationHandler notificationHandler) throws ResponseException {
        this.notificationHandler = notificationHandler;
        this.serverUrl = serverUrl;
        wsServer = new WebSocketFacade(serverUrl, notificationHandler);
        httpServer = new ServerFacade(serverUrl);
    }

    public static String eval(String input) {
        var tokens = input.toLowerCase().split(" ");
        var cmd = (tokens.length > 0) ? tokens[0] : "help";
        var params = Arrays.copyOfRange(tokens, 1, tokens.length);
        return switch (cmd) {
            case "quit" -> "Goodbye";
            case "joinplayer" -> joinPlayer(params);
            case "joinobserver" -> joinObserver();
            case "makemove" -> makeMove();
            case "leave" -> leave();
            case "resign" -> resign();
            case "help" -> help();
            default -> help();
        };
    }

    private static String joinPlayer(String... params) {
        // TODO: make this actually implement the given params
        try {
            System.out.println("before joinplayer() from the client");

            // test parameters so I don't have to keep typing them
            String auth = "wekqfhwpiurhfiufbfo";
            int game = 42;
            String color = "white";

            gameID = game;


            wsServer.joinPlayer(auth, game, color);


            System.out.println("after joinplayer() from the client");

            return "not yet implemented";
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    private static String joinObserver() {
        return "not yet implemented";
    }

    private static String makeMove() {
        return "not yet implemented";
    }

    private static String leave() {
        return "not yet implemented";
    }

    private static String resign() {
        return "not yet implemented";
    }


    private static String help() {
        // TODO: make it so they don't have to input the gameID
        return """
                    - joinPlayer <gameID> <playerColor>
                    - joinObserver <gameID>
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
