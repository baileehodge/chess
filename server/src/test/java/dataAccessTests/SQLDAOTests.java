package dataAccessTests;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.*;

import dataAccess.*;

import static org.junit.jupiter.api.Assertions.*;

class SQLUserDAOTest {
    public SQLGameDAO gameDAO = new SQLGameDAO();
    public SQLUserDAO userDAO = new SQLUserDAO();
    public SQLAuthDAO authDAO = new SQLAuthDAO();

    public GameService gameService = new GameService(gameDAO, authDAO);
    public AuthService authService = new AuthService(authDAO);
    public UserService userService = new UserService(userDAO, authDAO, authService);


    public ClearService clearService = new ClearService(userDAO,authDAO,gameDAO);

    SQLUserDAOTest() {
    }

    @BeforeEach
    public void clearAll() throws DataAccessException {
        clearService.clearAll();
    }

// AUTH
// clearAuths()
    @Test
    void clearAuthsPass() throws DataAccessException {
        AuthData authData = new AuthData("user", "1234");
        authDAO.createAuth(authData);
        assertDoesNotThrow(() -> {
            authDAO.clearAuths();
        });
    }

// createAuth()
    @Test
    void createAuthPass() {
        AuthData authData = new AuthData("user", "1234");
        assertDoesNotThrow(() -> {
            authDAO.createAuth(authData);
        });
    }
    @Test
    void createAuthFail() {
        AuthData authData = new AuthData("newt", "");
        assertThrows(DataAccessException.class, () -> authDAO.createAuth(authData));
    }
// deleteAuth()
    @Test
    void deleteAuthPass() throws DataAccessException {
        AuthData authData = new AuthData("user", "79");
        authDAO.createAuth(authData);
        assertDoesNotThrow(() -> {
            authDAO.deleteAuth("79");
        });

    }
    @Test
    void deleteAuthFail() throws DataAccessException {
        AuthData authData = new AuthData("user", "5");
        authDAO.createAuth(authData);
        assertThrows(DataAccessException.class, () -> authDAO.deleteAuth(""));
    }

// getAuth()

    @Test
    void getAuthPass() throws DataAccessException {
        AuthData authData = new AuthData("user", "79");
        authDAO.createAuth(authData);
        assertDoesNotThrow(() -> {
            authDAO.getAuth("79");
        });
    }
    @Test
    void getAuthFail() throws DataAccessException {
        AuthData authData = new AuthData("user", "5");
        authDAO.createAuth(authData);
        assertThrows(DataAccessException.class, () -> authDAO.getAuth(""));

    }
//
// USER
// clearUsers()

    @Test
    void clearUsersPass() throws DataAccessException {
        UserData userData = new UserData("user", "password", "@");
        userDAO.createUser(userData);
        assertDoesNotThrow(() -> {
            userDAO.clearUsers();
        });

    }
// getUser()
    @Test
    void getUserPass() throws DataAccessException {
        UserData userData = new UserData("user","pass","email@email");
        userDAO.createUser(userData);
        assertDoesNotThrow(() -> {
            userDAO.getUser("user");
        });

    }
    @Test
    void getUserFail() throws DataAccessException {
        UserData userData = new UserData("user","pass","email@email");
        userDAO.createUser(userData);
        assertThrows(DataAccessException.class, () -> userDAO.getUser(""));

    }
// createUser()
    @Test
    void createUserPass() {
        UserData userData = new UserData("user","pass","email@email");
        assertDoesNotThrow(() -> {
            userDAO.createUser(userData);
        });

    }
    @Test
    void createUserFail() {
        UserData userData = new UserData("","pass","email@email");
        assertThrows(DataAccessException.class, () -> userDAO.createUser(userData));
    }
//
// GAME
// clearGames()

    @Test
    void clearGamesPass() throws DataAccessException {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(1234, "Chidi", "Eleanor", "fork", game);
        gameDAO.createGame(gameData);
        assertDoesNotThrow(() -> {
            gameDAO.clearGames();
        });
    }
// listGames()

    @Test
    void listGamesOne() throws DataAccessException {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(1234, "Chidi", "Eleanor", "fork", game);
        gameDAO.createGame(gameData);
        assertDoesNotThrow(() -> {
            gameDAO.listGames();
        });

    }
    @Test
    void listGamesMany() throws DataAccessException {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(1234, "Chidi", "Eleanor", "fork", game);
        gameDAO.createGame(gameData);
        GameData gameData1 = new GameData(4321, "Leslie", "Ben", "Pawnee", game);
        gameDAO.createGame(gameData1);
        GameData gameData2 = new GameData(8, "April", "Andy", "halloween", game);
        gameDAO.createGame(gameData2);
        GameData gameData3 = new GameData(7, "Jean-Ralphio", "Mona-Lisa", "suspicious", game);
        gameDAO.createGame(gameData3);
        GameData gameData4 = new GameData(6, "Janet", "Michael", "frozen yogurt", game);
        gameDAO.createGame(gameData4);
        assertDoesNotThrow(() -> {
            gameDAO.listGames();
        });
    }
// createGame()

    @Test
    void createGamePass() {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(1234, "Chidi", "Eleanor", "fork", game);
        assertDoesNotThrow(() -> {
            gameDAO.createGame(gameData);
        });


    }
    @Test
    void createGameFail() throws DataAccessException {
        ChessGame game = new ChessGame();
        GameData gameData1 = new GameData(7, "Jean-Ralphio", "Mona-Lisa", "suspicious", game);
        gameDAO.createGame(gameData1);
        GameData gameData2 = new GameData(7, "Jean-Ralphio", "Mona-Lisa", "suspicious", game);
        assertThrows(DataAccessException.class, () -> gameDAO.createGame(gameData2));
    }
// getGame()

    @Test
    void getGamePass() throws DataAccessException {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(1234, "Chidi", "Eleanor", "fork", game);
        gameDAO.createGame(gameData);
        assertDoesNotThrow(() -> {
            gameDAO.getGame(1234);
        });
    }
    @Test
    void getGameFail() throws DataAccessException {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(-1, "Chidi", "Eleanor", "fork", game);
        gameDAO.createGame(gameData);
        assertThrows(DataAccessException.class, () -> gameDAO.getGame(gameData.getGameID()));
    }
// updateGame()

    @Test
    void updateGamePass() throws DataAccessException {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(1234, "Chidi", "Eleanor", "fork", game);
        gameDAO.createGame(gameData);
        assertDoesNotThrow(() -> {
            gameDAO.updateGame(gameData);
        });
    }
    @Test
    void updateGameFail() throws DataAccessException {
        ChessGame game = new ChessGame();
        GameData gameData = new GameData(-1, "Chidi", "Eleanor", "fork", game);
        gameDAO.createGame(gameData);
        assertThrows(DataAccessException.class, () -> gameDAO.updateGame(gameData));
    }

}