package ui;

import java.lang.reflect.Array;
import java.util.*;
import ui.UIException;

public class PreloginClient {


    public PreloginClient() {

    }
    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "help" -> help();
                case "quit" -> "quit";
                case "login" -> login(params);
                case "register" -> register();

                default -> help();
            };
        } catch (UIException ex) {
            return ex.getMessage();
        }
    }

    private String help() {
        // standard pre login stuff
        return null;
    }

    private String login(String... params) {
        return null;
    }
    private String register() {
        return null;
    }


}
