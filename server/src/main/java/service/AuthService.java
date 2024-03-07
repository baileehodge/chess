package service;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import model.*;

import java.util.UUID;

public class AuthService {

    private final AuthDAO dataAccess;

    public AuthService(AuthDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public AuthData createAuth(String username) throws DataAccessException {

        String token = UUID.randomUUID().toString();
        AuthData authData = new AuthData(username, token);

        return dataAccess.createAuth(authData);
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        dataAccess.deleteAuth(authToken);
    }

    public AuthData getAuth(String authToken) throws DataAccessException, ServiceException {
        if (dataAccess.getAuth(authToken) == null) {
            throw new ServiceException("Error: bad request");
        }
        return dataAccess.getAuth(authToken);
    }


}


// createAuth
// deleteAuth
// getAuth