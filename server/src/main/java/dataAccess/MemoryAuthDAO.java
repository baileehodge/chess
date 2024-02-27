package dataAccess;

import model.AuthData;

public class MemoryAuthDAO implements AuthDAO{
    // each of these had @Override at one point, placed automatically by the IDE

    public AuthData createAuth(String username) throws DataAccessException {
        return null;
    }


    public AuthData deleteAuth(String authToken) throws DataAccessException {
        return null;
    }


    public AuthData getAuth(String authToken) throws DataAccessException {
        return null;
    }
}

// createAuth
// deleteAuth
// getAuth
