package dataAccess;
import model.AuthData;
public interface AuthDAO {
    AuthData createAuth() throws DataAccessException;

    AuthData deleteAuth() throws DataAccessException;

    AuthData getAuth() throws DataAccessException;

}


// createAuth
// deleteAuth
// getAuth