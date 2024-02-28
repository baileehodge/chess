package dataAccess;
import model.UserData;

import javax.xml.crypto.Data;
import java.util.HashMap;

public interface UserDAO {

    void clearUsers() throws DataAccessException;

    UserData getUser(String username) throws DataAccessException;
    // return a username or something?

    UserData createUser(UserData userData) throws DataAccessException;

}

// getUser
// createUser
// login
// logout

