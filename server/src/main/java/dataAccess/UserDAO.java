package dataAccess;
import model.UserData;

import javax.xml.crypto.Data;

public interface UserDAO {
    UserData getUser(String username) throws DataAccessException;
    // return a username or something?

    UserData createUser(UserData userData) throws DataAccessException;
    // could this return a user object someday?

    UserData login(UserData userData) throws DataAccessException;

    UserData logout(String username) throws DataAccessException;

}

// getUser
// createUser
// login
// logout

