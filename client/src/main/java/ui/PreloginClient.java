package ui;

import server.Server;

import java.util.Arrays;

public class PreloginClient {
    private final int desiredPort;
    private final Server server;
    public PreloginClient(int desiredPort) {
        server = new Server();
        this.desiredPort = desiredPort;
    }
    // EVAL FUNCTION FROM PETSHOP
    // MODIFY TO FIT CHESS FUNCTIONALITY
//    public String eval(String input) {
//        try {
//            var tokens = input.toLowerCase().split(" ");
//            var cmd = (tokens.length > 0) ? tokens[0] : "help";
//            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
//            return switch (cmd) {
//                case "signin" -> signIn(params);
//                case "rescue" -> rescuePet(params);
//                case "list" -> listPets();
//                case "signout" -> signOut();
//                case "adopt" -> adoptPet(params);
//                case "adoptall" -> adoptAllPets();
//                case "quit" -> "quit";
//                default -> help();
//            };
//        } catch (ResponseException ex) {
//            return ex.getMessage();
//        }
//    }
}
