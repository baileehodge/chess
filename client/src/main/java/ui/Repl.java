package ui;

import model.GameData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private final PreloginClient preloginClient;
    private final PostloginClient postloginClient;
    private final GameplayClient gameplayClient;
    private static State state = State.SIGNEDOUT;
    private static String authToken = "";
    private static Collection<GameData> gameList = new ArrayList<>();
    public enum State {
        SIGNEDOUT,
        SIGNEDIN,
        INGAME
    }

    public void setGameList(Collection<GameData> newList) {
        gameList = newList;
    }
    public Collection<GameData> getGameList() {
        return gameList;
    }

    public static void setState(State newState) {
        state = newState;
    }
    public static void setToken(String token) {
        authToken = token;
    }
    public static String getToken() {
        return authToken;
    }

    public Repl(String serverUrl) {
        preloginClient = new PreloginClient(serverUrl);
        postloginClient = new PostloginClient(serverUrl);
        gameplayClient = new GameplayClient(serverUrl);
    }

    public void run() {
        System.out.println(SET_TEXT_COLOR_WHITE + "Welcome to Chess. Sign in or register to begin.");
        System.out.print(PreloginClient.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                if (state == State.INGAME) {
                    result = GameplayClient.eval(line);
                }
                else if (state == State.SIGNEDIN) {
                    result = PostloginClient.eval(line);
                }
                else {
                    result = PreloginClient.eval(line);
                }
                System.out.print(SET_TEXT_COLOR_WHITE + result);
            } catch (Throwable e) {
                System.out.print(e.getMessage());
            }
        }
        System.out.println();
    }

    private void printPrompt() {
        System.out.print("\n" + SET_TEXT_COLOR_BLUE + "[" + state + "] " + ">>> " + RESET_TEXT_COLOR);
    }

}
