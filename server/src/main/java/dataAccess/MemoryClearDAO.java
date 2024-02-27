package dataAccess;

import model.*;

import java.util.HashMap;


public class MemoryClearDAO implements ClearDAO {

    @Override
    public void clear() throws DataAccessException {
        Database.users = new HashMap<String, UserData>() {};
        Database.auths = new HashMap<String, AuthData>() {};
        Database.games = new HashMap<Object, GameData>() {};
        return;
    }
}
