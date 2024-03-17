package ui;

import java.util.Arrays;

public class PostloginClient {

    public static String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "quit" -> "Goodbye";
                case "logout" -> logout(params);
                case "createGame" -> createGame(params);
                case "listGames" -> listGames();
                case "joinGame" -> joinGame(params);
                case "joinObserver" -> joinObserver(params);

                default -> help();
            };
        } catch (UIException ex) {
            return ex.getMessage();
        }
    }

    private static String help() {
        return "postlogin help";

    }
    private static String logout(String... params) throws UIException{
        Repl.setState(Repl.State.SIGNEDOUT);
        return "logout null";

    }
    private static String createGame(String... params) throws UIException{
        return "createGame null";

    }
    private static String listGames() throws UIException{
        return "listGames null";

    }
    private static String joinGame(String... params) throws UIException{
        Repl.setState(Repl.State.INGAME);
        return "joinGame null";

    }
    private static String joinObserver(String... params) throws UIException{
        Repl.setState(Repl.State.INGAME);
        return "joinObserver null";

    }
}
