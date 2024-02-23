package dataAccess;

import model.UserData;
import org.eclipse.jetty.server.Authentication;

public class MemoryUserDAO implements UserDAO {
    @Override
    public UserData getUser() throws DataAccessException {
        return null;
    }

    @Override
    public UserData createUser() throws DataAccessException {
        return null;
    }

    @Override
    public UserData login() throws DataAccessException {
        return null;
    }

    @Override
    public UserData logout() throws DataAccessException {
        return null;
    }
}

