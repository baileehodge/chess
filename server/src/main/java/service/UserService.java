package service;
import dataAccess.DataAccessException;
import dataAccess.MemoryAuthDAO;
import dataAccess.MemoryUserDAO;
import dataAccess.UserDAO;
import model.*;

import java.util.Objects;

public class UserService {
    private final MemoryUserDAO userAccess;
    private final MemoryAuthDAO authAccess;

    public UserService(MemoryUserDAO userAccess, MemoryAuthDAO authAccess) {
        this.userAccess = userAccess;
        this.authAccess = authAccess;
    }

    public AuthData register(UserData user) throws DataAccessException, ServiceException {
        String username = user.getUsername();
        if (userAccess.getUser(username) != null) {
            throw new ServiceException("Error: already taken");
        }
        userAccess.createUser(user);
        return authAccess.createAuth(username);
    }
    public AuthData login(UserData user) throws DataAccessException, ServiceException {
        UserData recordedUser = userAccess.getUser(user.getUsername());
        // if it's the wrong password
        if ( recordedUser == null|| !Objects.equals(user.getPassword(), recordedUser.getPassword())) {
            throw new ServiceException("Error: unauthorized");
        }
        return authAccess.createAuth(user.getUsername());
    }
    public void logout(UserData user) throws DataAccessException {
        authAccess.getAuth(user.getUsername());
        authAccess.deleteAuth(authAccess.getAuth(user.getUsername()).toString());
    }
}


// getUser
// createUser
// login
// logout