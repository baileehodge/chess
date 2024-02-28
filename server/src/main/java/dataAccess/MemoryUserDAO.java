package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;
import org.eclipse.jetty.server.Authentication;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {
    static HashMap<String, UserData> users = new HashMap<String, UserData>();

    public void clearUsers() throws DataAccessException {
        users.clear();
    }
    @Override
    public UserData getUser(String username) throws DataAccessException {
        return users.get(username);
    }

    @Override
    public UserData createUser(UserData userData) throws DataAccessException {
        users.put(userData.getUsername(), userData);
        return userData;
    }

}

