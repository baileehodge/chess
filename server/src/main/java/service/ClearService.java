package service;
import dataAccess.*;

public class ClearService {
    // talks to the interfaces

    private static UserDAO userDAO = null;
    private static AuthDAO authDAO = null;
    private static GameDAO gameDAO = null;


    public ClearService(UserDAO userDAO, AuthDAO authDAO, GameDAO gameDAO) {
        ClearService.userDAO = userDAO;
        ClearService.authDAO = authDAO;
        ClearService.gameDAO = gameDAO;
    }


    public static void clearAll() throws DataAccessException {
        authDAO.clearAuths();
        gameDAO.clearGames();
        userDAO.clearUsers();
    }
}
