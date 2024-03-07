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
    private final AuthService authService;

    public UserService(MemoryUserDAO userAccess, MemoryAuthDAO authAccess, AuthService authService) {
        this.userAccess = userAccess;
        this.authAccess = authAccess;
        this.authService = authService;
    }

    public AuthData register(UserData user) throws DataAccessException, ServiceException {
        String username = user.getUsername();
        if (username == null || user.getPassword() == null || user.getEmail() == null) {
            throw new ServiceException("Error: bad request");
        }
        else if (userAccess.getUser(username) != null) {
            throw new ServiceException("Error: already taken");
        }
        userAccess.createUser(user);
        return authService.createAuth(username);
    }
    public AuthData login(UserData user) throws DataAccessException, ServiceException {
        UserData recordedUser = userAccess.getUser(user.getUsername());
        // if it's the wrong password
        if ( recordedUser == null|| !Objects.equals(user.getPassword(), recordedUser.getPassword())) {
            throw new ServiceException("Error: unauthorized");
        }
        return authService.createAuth(user.getUsername());
    }
    public void logout(String authToken) throws DataAccessException, ServiceException {
        if (authAccess.getAuth(authToken) == null) {
            throw new ServiceException("Error: unauthorized");
        }
        authAccess.deleteAuth(authToken);
    }
}


// getUser
// createUser
// login
// logout