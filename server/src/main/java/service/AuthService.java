package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import model.*;

public class AuthService {

    private final AuthDAO dataAccess;

    public AuthService(AuthDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthData createAuth(UserData user) throws DataAccessException {
        return dataAccess.createAuth();
    }

    public AuthData deleteAuth(UserData user) throws DataAccessException {

        return dataAccess.deleteAuth();
    }

    public AuthData getAuth(UserData user) throws DataAccessException {
        return dataAccess.getAuth();
    }


}


// createAuth
// deleteAuth
// getAuth