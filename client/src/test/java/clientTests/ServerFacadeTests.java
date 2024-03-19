package clientTests;

import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.*;
import server.Server;
import ui.ServerFacade;
import ui.UIException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade serverFacade = new ServerFacade("http://localhost:8080");


    @BeforeAll
    public static void init() throws UIException {
        serverFacade.clear();
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() throws UIException {
        serverFacade.clear();
        server.stop();
    }


    @Test
    public void loginTestPass() throws UIException {
        serverFacade.clear();
        // good username and password
        UserData testUser = new UserData("testboi1", "testboi1", "testboi1@byu.edu");
        serverFacade.register(testUser);
        assertDoesNotThrow(() -> {
            serverFacade.login(testUser);
        });
    }

    @Test
    public void loginTestFail() throws UIException {
        serverFacade.clear();
        // bad password
        UserData testUser = new UserData("testboi1", "testboi1", "testboi1@byu.edu");
        assertThrows(UIException.class, () -> {
            serverFacade.login(testUser);
        });
    }

    @Test
    public void registerTestPass() throws UIException {
        serverFacade.clear();
        UserData testUser = new UserData("testboi1", "testboi1", "testboi1@byu.edu");
        assertDoesNotThrow(() -> {
            serverFacade.register(testUser);
        });
    }

    @Test
    public void registerTestFail() throws UIException {
        serverFacade.clear();
        UserData testUser = new UserData(null, null, null);
        assertThrows(UIException.class, () -> {
            serverFacade.login(testUser);
        });
    }
    @Test
    public void logoutTestPass() throws UIException {
        serverFacade.clear();
        UserData testUser = new UserData("testboi1", "testboi1", "testboi1@byu.edu");
        var person = serverFacade.register(testUser);
        AuthData authData = new AuthData(null, person.getAuthToken());
        assertDoesNotThrow(() -> {
            serverFacade.logout(authData);
        });
    }

    @Test
    public void logoutTestFail() throws UIException {
        serverFacade.clear();
        AuthData authData = new AuthData(null, "thisisnotanauthtoken");

        assertThrows(UIException.class, () -> {
            serverFacade.logout(authData);
        });
    }
    @Test
    public void createGameTestPass() throws UIException {
        serverFacade.clear();
        UserData testUser = new UserData("testboi1", "testboi1", "testboi1@byu.edu");
        var token = serverFacade.register(testUser).getAuthToken();
        GameData gameData = new GameData(null,null,null, "cheddar");
        assertDoesNotThrow(() -> {
            serverFacade.createGame(gameData, token);
        });
    }

    @Test
    public void createGameTesFail() throws UIException {
        serverFacade.clear();
        var token = "notarealauthtoken";
        GameData gameData = new GameData(null,null,null, "cheddar");
        assertThrows(UIException.class, () -> {
            serverFacade.createGame(gameData, token);
        });
    }
    @Test
    public void listGamesTestPass() throws UIException {
        serverFacade.clear();
        UserData testUser = new UserData("testboi1", "testboi1", "testboi1@byu.edu");
        var token = serverFacade.register(testUser).getAuthToken();
        assertDoesNotThrow(() -> {
            serverFacade.listGames(token);
        });
    }

    @Test
    public void listGamesTestFail() throws UIException {
        serverFacade.clear();
        var token = "definitelynotanauth";
        assertThrows(UIException.class, () -> {
            serverFacade.listGames(token);
        });
    }
    @Test
    public void joinGameTestPass() throws UIException {
        serverFacade.clear();
        UserData testUser = new UserData("testboi1", "testboi1", "testboi1@byu.edu");
        var token = serverFacade.register(testUser).getAuthToken();

        GameData gameData = new GameData(null,null,null, "cheddar");
        serverFacade.createGame(gameData, token);

        assertThrows(UIException.class, () -> {
            serverFacade.joinGame(token, "WHITE", 7);
        });
    }

    @Test
    public void joinGameTestFail() throws UIException {
        serverFacade.clear();
        assertThrows(UIException.class, () -> {
            serverFacade.joinGame("definitelynotanauth", "beige", 7);
        });
    }
    @Test
    public void joinObserverTestPass() throws UIException {
        serverFacade.clear();
        UserData testUser = new UserData("testboi1", "testboi1", "testboi1@byu.edu");
        var token = serverFacade.register(testUser).getAuthToken();
        assertThrows(UIException.class, () -> {
            serverFacade.joinGame(token, null, 7);
        });
    }
    @Test
    public void joinObserverTestFail() throws UIException {
        serverFacade.clear();
        assertThrows(UIException.class, () -> {
            serverFacade.joinGame("definitelynotanauth", null, 7);
        });
    }



}
