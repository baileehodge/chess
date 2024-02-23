package dataAccess;
import model.UserData;

import javax.xml.crypto.Data;

public interface UserDAO {
    UserData getUser() throws DataAccessException;
    // return a username or something?

    UserData createUser() throws DataAccessException;
    // could this return a user object someday?

    UserData login() throws DataAccessException;

    UserData logout() throws DataAccessException;

}

// getUser
// createUser
// login
// logout

