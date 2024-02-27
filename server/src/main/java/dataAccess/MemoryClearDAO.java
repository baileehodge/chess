package dataAccess;

import model.UserData;

import java.util.HashMap;


public class MemoryClearDAO implements ClearDAO {

    @Override
    public void clear() throws DataAccessException {
        Database.users = new HashMap<String, UserData>() {};
        Database.auths = new HashMap<String, UserData>() {};
        Database.games = new HashMap<Integer, UserData>() {};
        return;
    }
}
