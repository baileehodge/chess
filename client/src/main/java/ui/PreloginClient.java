package ui;

import java.lang.reflect.Array;
import java.util.*;
import ui.UIException;

public class PreloginClient {


    public PreloginClient() {

    }
    public static String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "quit" -> "Goodbye";
                case "login" -> login(params);
                case "register" -> register();

                default -> help();
            };
        } catch (UIException ex) {
            return ex.getMessage();
        }
    }

    public static String help() {
        return """
                    - login <username> <password>
                    - register <username> <password> <email>
                    - quit
                    """;
    }

    private static String login(String... params) throws UIException{

        Repl.setState(Repl.State.SIGNEDIN);
        return "null";
    }
    private static String register() throws UIException{

        Repl.setState(Repl.State.SIGNEDIN);
        return "null";
    }


}
