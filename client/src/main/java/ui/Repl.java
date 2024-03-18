package ui;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl {
    private final PreloginClient preloginClient;
    private final PostloginClient postloginClient;
    private final GameplayClient gameplayClient;
    private static State state = State.SIGNEDOUT;
    public enum State {
        SIGNEDOUT,
        SIGNEDIN,
        INGAME
    }

    public static void setState(State newState) {
        state = newState;
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
        System.out.print("\n" + SET_TEXT_COLOR_BLUE + ">>> " + RESET_TEXT_COLOR);
    }

}
