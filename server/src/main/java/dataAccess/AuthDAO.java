package dataAccess;
import model.AuthData;
public interface AuthDAO {
    AuthData createAuth(String username) throws DataAccessException;

    AuthData deleteAuth(String authToken) throws DataAccessException;

    AuthData getAuth(String authToken) throws DataAccessException;

}


// createAuth
// deleteAuth
// getAuth