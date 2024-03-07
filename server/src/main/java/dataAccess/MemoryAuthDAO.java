package dataAccess;

import model.AuthData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO{
    // each of these had @Override at one point, placed automatically by the IDE

    static HashMap<String, AuthData> auths = new HashMap<String, AuthData>();

    public void clearAuths() {
        auths.clear();
    }

    public AuthData createAuth(AuthData auth) {
        // could make this check that the username is valid

        auths.put(auth.getAuthToken(), auth);
        return auth;
    }


    public void deleteAuth(String authToken) {
        auths.remove(authToken);
    }


    public AuthData getAuth(String authToken) {
        return auths.get(authToken);
    }
}

// createAuth
// deleteAuth
// getAuth
