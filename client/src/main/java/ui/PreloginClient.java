package ui;

import java.util.*;

import model.UserData;

public class PreloginClient {
    static ServerFacade serverFacade;

    public PreloginClient(String serverUrl) {
        serverFacade = new ServerFacade(serverUrl);

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
        if (params.length >= 2) {

            Repl.setState(Repl.State.SIGNEDIN);
            return "login null";
        }
        throw new UIException(400, "expected login <username> <password>");
    }
    private static String register(String... params) throws UIException{
        if (params.length >= 4) {
            UserData user = new UserData(params[1],params[2], params[3]);
            serverFacade.register(user);

            // TODO: provide for when the register fails

            Repl.setState(Repl.State.SIGNEDIN);
            return "account created and user signed in";
        }
        throw new UIException(400, "expected register <username> <password> <email>");
    }


}
