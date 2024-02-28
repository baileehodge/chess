package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.HashMap;

public class MemoryAuthDAO implements AuthDAO{
    // each of these had @Override at one point, placed automatically by the IDE

    static HashMap<String, AuthData> auths = new HashMap<String, AuthData>();

    public void clearAuths() throws DataAccessException {
        auths.clear();
    }

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
