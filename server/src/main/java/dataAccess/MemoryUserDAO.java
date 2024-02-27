package dataAccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;

public class MemoryUserDAO implements UserDAO {
    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public UserData createUser(UserData userData) throws DataAccessException {
        return null;
    }

    @Override
    public UserData login(UserData userData) throws DataAccessException {
        return null;
    }

    @Override
    public UserData logout(String username) throws DataAccessException {
        return null;
    }
}

