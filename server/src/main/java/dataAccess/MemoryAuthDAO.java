package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
    // each of these had @Override at one point, placed automatically by the IDE

    static HashMap<String, AuthData> auths = new HashMap<String, AuthData>();

    public void clearAuths() throws DataAccessException {
        auths.clear();
    }

    public AuthData createAuth(AuthData auth) throws DataAccessException {
        // could make this check that the username is valid

        auths.put(auth.getAuthToken(), auth);
        return auth;
    }


    public void deleteAuth(String authToken) throws DataAccessException {
        auths.remove(authToken);
    }


    public AuthData getAuth(String authToken) throws DataAccessException {
        return auths.get(authToken);
    }
}

// createAuth
// deleteAuth
// getAuth
