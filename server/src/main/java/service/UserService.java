package service;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import model.*;

public class UserService {
    private final UserDAO dataAccess;

    public UserService(UserDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public UserData getUSer(UserData user) throws DataAccessException {
        return dataAccess.getUser();
    }
    public UserData createUser(UserData user) throws DataAccessException {
        return dataAccess.createUser();
    }
    public UserData login(UserData user) throws DataAccessException {
        return dataAccess.login();
    }
    public UserData logout(UserData user) throws DataAccessException {
        return dataAccess.logout();
    }
}


// getUser
// createUser
// login
// logout