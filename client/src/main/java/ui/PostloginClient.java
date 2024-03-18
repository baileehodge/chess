package ui;

import model.AuthData;
import model.GameData;

import java.util.Arrays;

import static ui.Repl.*;

public class PostloginClient {
    static ServerFacade serverFacade;

    public PostloginClient(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);
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
        } catch (UIException ex) {
            return ex.getMessage();
        }
    }

    private static String help() {
        return """
                    - logout
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
        //TODO: return multiple games
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
    private static String joinGame(String... params) throws UIException{
        if (params.length >= 2) {
            serverFacade.joinGame(getToken(), params[1], Integer.parseInt(params[0]));
            Repl.setState(Repl.State.INGAME);
            return "joined game as player";
        }
        throw new UIException(400, "expected joinGame <gameID> <playerColor>");
    }
    private static String joinObserver(String... params) throws UIException{
        //record - authtoken, player color, game id
        serverFacade.joinGame(getToken(), null,  Integer.parseInt(params[0]));
        Repl.setState(Repl.State.INGAME);
        return "joined game as observer";
    }
}
