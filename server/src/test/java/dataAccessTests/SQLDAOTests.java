package dataAccessTests;

import dataAccess.DataAccessException;
import dataAccess.SQLAuthDAO;
import dataAccess.SQLGameDAO;
import dataAccess.SQLUserDAO;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.*;
import service.requests.JoinRecord;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SQLUserDAOTest {
    public SQLGameDAO gameDAO = new SQLGameDAO();
    public SQLUserDAO userDAO = new SQLUserDAO();
    public SQLAuthDAO authDAO = new SQLAuthDAO();

    public GameService gameService = new GameService(gameDAO, authDAO);
    public AuthService authService = new AuthService(authDAO);
    public UserService userService = new UserService(userDAO, authDAO, authService);


    public ClearService clearService = new ClearService(userDAO,authDAO,gameDAO);

    SQLUserDAOTest() throws DataAccessException {
    }

    @BeforeEach
    public void clearAll() throws ServiceException, DataAccessException {
        clearService.clearAll();
    }
    @Test
    void clearServiceSuccess() throws ServiceException, DataAccessException {
        clearService.clearAll();
        // register a user
        UserData userData = new UserData("user", "pass", "cosm0@byu.edu");
        userService.register(userData);
        // create a game
        String auth = authService.createAuth("user").getAuthToken();
        gameService.createGame("test game", auth);
        // clear it all
        assertDoesNotThrow(() -> {
            clearService.clearAll();
        });

    }

    @Test
    void listGamesSuccess() throws DataAccessException, ServiceException {
        // create 2 games
        String auth = authService.createAuth("user").getAuthToken();
        gameService.createGame("test game 1", auth);
        gameService.createGame("test game 2", auth);
        // check that listGames lists 2 games
        assertEquals(2, gameService.listGames(auth).size());
    }

    @Test
    void listGamesFail() throws DataAccessException, ServiceException {
        // create 2 games
        String auth = authService.createAuth("user").getAuthToken();
        gameService.createGame("test game 1", auth);
        gameService.createGame("test game 2", auth);
        // try to list games using a fake auth token
        String fakeAuth = "beef";
        ServiceException exception = assertThrows(ServiceException.class, () -> gameService.listGames(fakeAuth));
        assertEquals("Error: unauthorized", exception.getMessage());    }

    @Test
    void createGameSuccess() throws DataAccessException, ServiceException {
        // create a game with no errors thrown
        String auth = authService.createAuth("user").getAuthToken();
        assertDoesNotThrow(() -> {
            gameService.createGame("test game", auth);
        });

    }

    @Test
    void createGameFail() {
        // create a game with a faulty auth token
        String fakeAuth = "beef";
        // make sure it throws an error
        ServiceException exception = assertThrows(ServiceException.class, () -> gameService.createGame("test game",fakeAuth));
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    void joinGameSuccess() throws DataAccessException, ServiceException {
        // create a game
        String auth = authService.createAuth("user").getAuthToken();
        var game = gameService.createGame("test game", auth);
        // make a join record to pass in
        JoinRecord joinRecord = new JoinRecord(auth, "WHITE", game.getGameID());
        // join a game and don't throw an error
        assertDoesNotThrow(() -> {
            gameService.joinGame(joinRecord);
        });
    }

    @Test
    void joinGameFail() throws DataAccessException, ServiceException {
        // join a game as green
        // create a game
        String auth = authService.createAuth("user").getAuthToken();
        var game = gameService.createGame("test game", auth);
        // make a join record to pass in
        JoinRecord joinRecord = new JoinRecord(auth, "GREEN", game.getGameID());
        // error expected
        ServiceException exception = assertThrows(ServiceException.class, () -> gameService.joinGame(joinRecord));
        assertEquals("Error: bad request", exception.getMessage());
    }

    @Test
    void createAuthSuccess() throws ServiceException, DataAccessException {
        // register a user
        UserData userData = new UserData("user", "pass", "cosm0@byu.edu");
        userService.register(userData);
        // make an auth for that user without throwing an error
        assertDoesNotThrow(() -> {
            authService.createAuth(userData.getUsername());
        });
    }
    @Test
    void createAuthFail() throws ServiceException, DataAccessException {
        // not exactly a fail case, but a useful test nonetheless
        // see MemoryAuthDAO.java createAuth method fo ideas on making an actual fail case

        // register 2 users
        UserData userData1 = new UserData("user1", "pass1", "cosm0@byu.edu");
        UserData userData2 = new UserData("user2", "pass2", "cosm0@byu.edu");
        userService.register(userData1);
        userService.register(userData2);
        // make an auth for each user
        String auth1 = authService.createAuth(userData1.getUsername()).getAuthToken();
        String auth2 = authService.createAuth(userData2.getUsername()).getAuthToken();
        // assert the auths are not equal
        assertNotEquals(auth1, auth2);
    }

    @Test
    void deleteAuthSuccess() throws DataAccessException {
        // create an auth
        UserData userData = new UserData("user1", "pass1", "cosm0@byu.edu");
        String auth = authService.createAuth(userData.getUsername()).getAuthToken();
        // delete the auth without issue
        assertDoesNotThrow(() -> {
            authService.deleteAuth(auth);
        });
    }

    @Test
    void deleteAuthFail() throws DataAccessException {
        // create an auth
        UserData userData = new UserData("user1", "pass1", "cosm0@byu.edu");
        String auth = authService.createAuth(userData.getUsername()).getAuthToken();
        // delete the auth
        authService.deleteAuth(auth);
        // try to use the auth and make sure you get an error
        ServiceException exception = assertThrows(ServiceException.class, () -> gameService.createGame("test game",auth));
        assertEquals("Error: unauthorized", exception.getMessage());
    }

    @Test
    void getAuthSuccess() throws DataAccessException {
        // returns the full auth data object when given an auth token

        // create an auth token
        UserData userData = new UserData("user1", "pass1", "cosm0@byu.edu");
        String auth = authService.createAuth(userData.getUsername()).getAuthToken();
        // get the auth token without throwing an error
        assertDoesNotThrow(() -> {
            authService.getAuth(auth);
        });

    }

    @Test
    void getAuthFail() throws DataAccessException, ServiceException {
        // don't create an auth token
        // try to get the auth token and expect it to throw an error
        ServiceException exception = assertThrows(ServiceException.class, () -> authService.getAuth("beef"));
        assertEquals("Error: bad request", exception.getMessage());

    }

}