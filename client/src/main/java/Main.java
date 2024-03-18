import chess.ChessGame;
import chess.ChessPiece;


import server.Server;
import ui.Repl;
import org.junit.jupiter.api.*;


public class Main {
    public static void main(String[] args) {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new Repl(serverUrl).run();
    }
}