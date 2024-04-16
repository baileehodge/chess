package ui;

import WebSocketMessages.ResponseException;
import WebSocketMessages.serverMessages.ErrorMessage;
import WebSocketMessages.serverMessages.LoadGameMessage;
import WebSocketMessages.serverMessages.NotificationMessage;
import WebSocketMessages.serverMessages.ServerMessage;
import chess.*;
import model.GameData;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.util.Arrays;

import static chess.ChessGame.TeamColor.NONE;
import static ui.DrawBoard.drawBoard;
import static ui.Repl.getRole;
import static ui.Repl.getToken;

public class GameplayClient implements NotificationHandler{

    static ServerFacade serverFacade;
    static int gameID;
    public static String serverURL;
    public static WebSocketFacade ws;
    static NotificationHandler notificationHandler;



    public GameplayClient(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);
        serverURL = serverUrl;
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
        } catch (ResponseException | UIException e) {
            return e.getMessage();
        }
    }



    private static String help() {
        return """
                    - help
                    - redraw
                    - move <startRow> <startCol> <endRow> <endCol> <promotion(when applicable)>
                    - highlight <piece> (Format: 3,4 . Highlights legal moves for that piece.)
                    - leave
                    - resign
                    """;
    }

    private static String redrawBoard() throws UIException {
        ChessGame.TeamColor color = getRole();
        ChessBoard board = null;

        GameData[] list = serverFacade.listGames(getToken());

        for (int i = 0; i < list.length; i++) {
            if (list[i].getGameID() == gameID) {
                board = list[i].getGame().getBoard();
            }
        }
        if (board == null){
            throw new UIException(500, "The game no longer exists");
        }


        if (color == NONE) {
            color = null;
        }

        drawBoard(board, color);

        return "\n";
    }

    private static String move(String... params) throws ResponseException, UIException {

        ChessPosition start = new ChessPosition(Integer.parseInt(params[0]), convertToNum(params[1]));
        ChessPosition end = new ChessPosition(Integer.parseInt(params[2]), convertToNum(params[3]));

//        Implement promo??
//        if (params.length >= 5) {
//            ws = new WebSocketFacade(serverURL, notificationHandler);
//            ws.movePiece(getToken(),getGameID(),start,end,promo);
//        }
//        else {
//            ws = new WebSocketFacade(serverURL, notificationHandler);
//            ws.movePiece(getToken(),getGameID(),start,end,null);
//        }

        ws = new WebSocketFacade(serverURL, notificationHandler);
        ws.movePiece(getToken(),getGameID(),start,end,null);

        redrawBoard();

        return String.format("Moved from %d,%s to %d,%s.\n", start.getRow(), convertToLetter(start.getColumn()), end.getRow(), convertToLetter(end.getColumn()));
    }

    private static int convertToNum(String letter) {
        return switch (letter) {
            case "a" -> 1;
            case "b" -> 2;
            case "c" -> 3;
            case "d" -> 4;
            case "e" -> 5;
            case "f" -> 6;
            case "g" -> 7;
            case "h" -> 8;
            default -> 0;
        };
    }
    private static String convertToLetter(int number) {
        return switch (number) {
            case 1 -> "a";
            case 2 -> "b";
            case 3 -> "c";
            case 4 -> "d";
            case 5 -> "e";
            case 6 -> "f";
            case 7 -> "g";
            case 8 -> "h";
            default -> "q";
        };
    }



    private static String leave() throws ResponseException {
        String authToken = getToken();

        ws = new WebSocketFacade(serverURL, notificationHandler);
        ws.leave(authToken, gameID);

        Repl.setState(Repl.State.SIGNEDIN);

        return "You left the game\n";
    }


    private static String resign() throws ResponseException {
        String authToken = getToken();

        ws = new WebSocketFacade(serverURL, notificationHandler);
        ws.resign(authToken, gameID);


        return "You resigned from the game.\n\n Your opponent wins.";
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


    @Override
    public void notify(NotificationMessage notification) {
        System.out.println(notification.getMessage());

    }

    @Override
    public void loadGame(LoadGameMessage notification) {
        System.out.println(notification.getServerMessageText());

    }

    @Override
    public void error(ErrorMessage notification) {
        System.out.println(notification.getErrorMessage());
    }
}