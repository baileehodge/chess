package service;
import dataAccess.*;

public class ClearService {
    // talks to the interfaces

    private static UserDAO userDAO = new MemoryUserDAO();
    private static AuthDAO authDAO = new MemoryAuthDAO();
    private static GameDAO gameDAO = new MemoryGameDAO();



    public static void clearAll() throws DataAccessException {
        authDAO.clearAuths();
        gameDAO.clearGames();
        userDAO.clearUsers();
    }
}
