package ui;

public class Repl {
    private final PreloginClient preloginClient;
    private final PostloginClient postloginClient;
    private final GameplayClient gameplayClient;

    public Repl(String serverUrl) {
        preloginClient = new PreloginClient(serverUrl);
        postloginClient = new PostloginClient(serverUrl);
        gameplayClient = new GameplayClient(serverUrl);
    }

    public void run() {
    }
}
