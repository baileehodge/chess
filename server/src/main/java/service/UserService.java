package service;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.*;

public class UserService {
    private final UserDAO dataAccess;

    public UserService(UserDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public UserData getUser(String username) throws DataAccessException {
        return dataAccess.getUser(username);
    }
    public UserData createUser(UserData user) throws DataAccessException {
        return dataAccess.createUser(user);
    }
    public UserData login(UserData user) throws DataAccessException {
        return null;
    }
    public UserData logout(String username) throws DataAccessException {
        return null;
    }
}


// getUser
// createUser
// login
// logout