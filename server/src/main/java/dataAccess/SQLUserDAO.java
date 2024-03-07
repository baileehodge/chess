package dataAccess;

import model.AuthData;
import model.UserData;

import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {

    // START create db tables on startup if they don't already exist
    public SQLUserDAO() throws DataAccessException {
        configureDatabase();
    }

    // TODO: sql unverified
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  users (
              `username` varchar(256) NOT NULL,
              `email` varchar(256) NOT NULL,
              `password` varchar(256) NOT NULL,
              
              PRIMARY KEY (`username`),
              INDEX(email),
              INDEX(password)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Unable to configure user database"); //500
        }
    }

    // END new database creation

    @Override
    public void clearUsers() throws DataAccessException {
        // drop the users table if it exists
        // create a fresh one
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        // select from the user table by username
        // primary key of the table might need to be username
        // return the user as a UserData object
        return null;
    }

    @Override
    public UserData createUser(UserData userData) throws DataAccessException {
        // take the UserData object and make a row in the user table for it
        return null;
    }
}
