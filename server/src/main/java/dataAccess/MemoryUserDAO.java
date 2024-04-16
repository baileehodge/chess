package dataAccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO implements UserDAO {
    static HashMap<String, UserData> users = new HashMap<String, UserData>();

    public void clearUsers() {
        users.clear();
    }
    @Override
    public UserData getUser(String username) {
        return users.get(username);
    }

    @Override
    public UserData createUser(UserData userData) {
        users.put(userData.getUsername(), userData);
        return userData;
    }

}

