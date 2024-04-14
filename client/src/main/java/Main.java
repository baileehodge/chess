import WebSocketMessages.ResponseException;
import ui.Repl;


// CLIENT MAIN
public class Main {
    public static void main(String[] args) throws ResponseException {
        var serverUrl = "http://localhost:8080";
        if (args.length == 1) {
            serverUrl = args[0];
        }

        new Repl(serverUrl).run();
    }
}