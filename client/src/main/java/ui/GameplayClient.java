package ui;

import WebSocketMessages.ResponseException;
import chess.ChessBoard;
import chess.ChessGame;
import dataAccess.DataAccessException;
import model.GameData;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.util.Arrays;

import static chess.ChessGame.TeamColor.NONE;
import static ui.DrawBoard.drawBoard;
import static ui.Repl.getRole;
import static ui.Repl.getToken;

public class GameplayClient {

    static ServerFacade serverFacade;
    static int gameID;
    public static String serverURL;
    public static WebSocketFacade ws;
    static NotificationHandler notificationHandler;



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




    public static String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                // redundant: case "help" -> help();
                case "redraw" -> redrawBoard();
                case "leave" -> leave();
                case "move" -> move(params);
                case "resign" -> resign();
                case "highlight" -> highlight(params);
                default -> help();
            };
        } catch (ResponseException | DataAccessException | UIException e) {
            return e.getMessage();
        }
    }



    private static String help() {
        return """
                    - help
                    - redraw
                    - move <move> (Format: 3,4:4,5)
                    - highlight <piece> (Format: 3,4 . Highlights legal moves for that piece.)
                    - leave
                    - resign
                    """;
    }

    private static String redrawBoard() throws DataAccessException, UIException {
        ChessGame.TeamColor color = getRole();
        ChessBoard board = null;

        GameData[] list = serverFacade.listGames(getToken());

        for (int i = 0; i < list.length; i++) {
            if (list[i].getGameID() == gameID) {
                board = list[i].getGame().getBoard();
            }
        }
        if (board == null){
            throw new DataAccessException("you're not real");
        }


        if (color == NONE) {
            color = null;
        }

        drawBoard(board, color);

        return "\n";
    }

    private static String move(String... params) {
        return "Allow the user to input what move they want to make. The board is updated to reflect the result of the move, and the board automatically updates on all clients involved in the game.\n";
    }

    private static String leave() throws ResponseException {
        String authToken = getToken();

        ws = new WebSocketFacade(serverURL, notificationHandler);
        ws.leave(authToken, gameID);

        Repl.setState(Repl.State.SIGNEDIN);

        return "You left the game\n";
    }


    private static String resign() {
        return "Prompts the user to confirm they want to resign. If they do, the user forfeits the game and the game is over. Does not cause the user to leave the game.\n";
    }

    private static String highlight(String... params) {
        return "Allows the user to input what piece for which they want to highlight legal moves. The selected piece’s current square and all squares it can legally move to are highlighted. This is a local operation and has no effect on remote users’ screens.\n";
    }




    // Draws a basic starting board
    // probably obsolete for phase 6
    private static String drawBasic() {
        // draws a starting board
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        drawBoard(board, null);
        return "\n";
    }
}