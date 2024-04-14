package ui;

import WebSocketMessages.ResponseException;
import WebSocketMessages.userCommands.JoinPlayerCommand;
import WebSocketMessages.userCommands.UserGameCommand;
import chess.ChessGame;
import model.AuthData;
import model.GameData;
import websocket.NotificationHandler;
import websocket.WebSocketFacade;

import java.util.Arrays;

import static ui.Repl.*;

public class PostloginClient {
    static ServerFacade serverFacade;
    public static String serverUrl;
    static NotificationHandler notificationHandler;
    public static WebSocketFacade ws;

    public PostloginClient(String serverUrl) {

        serverFacade = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public static String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "quit" -> "Goodbye";
                case "logout" -> logout();
                case "creategame" -> createGame(params);
                case "listgames" -> listGames();
                case "joingame" -> joinGame(params);
                case "joinobserver" -> joinObserver(params);

                default -> help();
            };
            //  should v these v be the same as each other? Probably
        } catch (UIException ex) {
            return ex.getMessage();
        } catch (ResponseException e) {
            throw new RuntimeException(e);
        }
    }

    private static String help() {
        return """
                    - logout
                    - listGames
                    - createGame <gameName>
                    - joinGame <gameID> <playerColor>
                    - joinObserver <gameID>
                    - quit
                    """;
    }
    private static String logout() throws UIException{
        AuthData auth = new AuthData("", getToken());
        serverFacade.logout(auth);
        Repl.setState(Repl.State.SIGNEDOUT);
        return "logout successful";
    }
    private static String createGame(String... params) throws UIException{
        if (params.length >= 1) {
            GameData game = new GameData(null, null, null, params[0]);
            serverFacade.createGame(game, getToken());
            return "game created with the following name: " + params[0];
        }
        throw new UIException(400, "expected CreateGame <gameName>");
    }
    private static String listGames() throws UIException{
        GameData[] list = serverFacade.listGames(getToken());
        StringBuilder returnString = new StringBuilder();
        returnString.append("---All games--- \n");
        for (int i = 0; i < list.length; i++) {
            returnString.append(list[i].getGameID() +": ");
            returnString.append(list[i].getGameName());
            returnString.append("\n");
        }

        return returnString.toString();
    }

    // TO DONE: modify both of these to do websocket things
    private static String joinGame(String... params) throws UIException, ResponseException {
        if (params.length >= 2) {
            ChessGame.TeamColor teamColor = ChessGame.TeamColor.valueOf(params[1].toUpperCase());
            String authToken = getToken();
            int gameID = Integer.parseInt(params[0]);

            serverFacade.joinGame(getToken(), params[1], Integer.parseInt(params[0]));

            ws = new WebSocketFacade(serverUrl, notificationHandler);
            ws.joinPlayer(authToken, gameID, teamColor);

            Repl.setState(Repl.State.INGAME);
            GameplayClient.setGameID(gameID);
            return "joined game as player";
        }
        throw new UIException(400, "expected joinGame <gameID> <playerColor>");
    }
    private static String joinObserver(String... params) throws UIException, ResponseException {
        String authToken = getToken();
        int gameID = Integer.parseInt(params[0]);

        serverFacade.joinGame(authToken, null,  gameID);

        ws = new WebSocketFacade(serverUrl, notificationHandler);
        ws.joinObserver(authToken, gameID);

        Repl.setState(Repl.State.INGAME);
        GameplayClient.setGameID(Integer.parseInt(params[0]));
        return "joined game as observer";
    }
}
