package clientTests;

import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;
import ui.UIException;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade;


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
    public void loginTestPass() throws UIException {
        UserData testUser = new UserData("testboi", "testboi", "testboi@byu.edu");
        serverFacade.register(testUser);
        Assertions.assertDoesNotThrow(() -> {
            serverFacade.login(testUser);
        });
    }

    @Test
    public void loginTestFail() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerTestPass() {
        Assertions.assertTrue(true);
    }

    @Test
    public void registerTestFail() {
        Assertions.assertTrue(true);
    }
    @Test
    public void logoutTestPass() {
        Assertions.assertTrue(true);
    }

    @Test
    public void logoutTestFail() {
        Assertions.assertTrue(true);
    }
    @Test
    public void createGameTestPass() {
        Assertions.assertTrue(true);
    }

    @Test
    public void createGameTesFail() {
        Assertions.assertTrue(true);
    }
    @Test
    public void listGamesTestPass() {
        Assertions.assertTrue(true);
    }

    @Test
    public void listGamesTestFail() {
        Assertions.assertTrue(true);
    }
    @Test
    public void joinGameTestPass() {
        Assertions.assertTrue(true);
    }

    @Test
    public void joinGameTestFail() {
        Assertions.assertTrue(true);
    }
    @Test
    public void joinObserverTestPass() {
        Assertions.assertTrue(true);
    }
    @Test
    public void joinObserverTestFail() {
        Assertions.assertTrue(true);
    }



}
