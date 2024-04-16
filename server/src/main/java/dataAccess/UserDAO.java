package dataAccess;
import model.UserData;

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

