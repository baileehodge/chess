package service;
import dataAccess.*;

public class ClearService {
    // talks to the interfaces

    private final UserDAO userDAO;
    private final AuthDAO authDAO;
    private final GameDAO gameDAO;

    public ClearService(UserDAO userDAO, AuthDAO authDao, GameDAO gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDao;
        this.gameDAO = gameDAO;

    }





    public void clearAll() throws DataAccessException {
        authDAO.clearAuths();
        gameDAO.clearGames();
        userDAO.clearUsers();
    }
}
