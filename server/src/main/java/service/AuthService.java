package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import model.*;

public class AuthService {

    private final AuthDAO dataAccess;

    public AuthService(AuthDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthData createAuth(String username) throws DataAccessException {
        return dataAccess.createAuth(username);
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        dataAccess.deleteAuth(authToken);
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        return dataAccess.getAuth(authToken);
    }


}


// createAuth
// deleteAuth
// getAuth