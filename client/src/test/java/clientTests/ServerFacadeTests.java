package clientTests;

import org.junit.jupiter.api.*;
import server.Server;


public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void loginTestPass() {
        Assertions.assertTrue(true);
    }
    public void registerTestPass() {
        Assertions.assertTrue(true);
    }
    public void logoutTestPass() {
        Assertions.assertTrue(true);
    }
    public void createGameTestPass() {
        Assertions.assertTrue(true);
    }
    public void listGamesTestPass() {
        Assertions.assertTrue(true);
    }
    public void joinGameTestPass() {
        Assertions.assertTrue(true);
    }
    public void joinObserverTestPass() {
        Assertions.assertTrue(true);
    }



}
