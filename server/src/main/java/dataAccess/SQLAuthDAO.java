package dataAccess;

import model.AuthData;

import java.sql.SQLException;

public class SQLAuthDAO implements AuthDAO{

    // START create db tables on startup if they don't already exist
    public SQLAuthDAO() throws DataAccessException {
        configureDatabase();
    }

    // TODO: sql unverified
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auths (
              `auth` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,

              
              PRIMARY KEY (`id`),
              FOREIGN KEY ('username')
            )
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
            throw new DataAccessException("Unable to configure auth database"); //500
        }
    }

    // END new database creation


    public void clearAuths() throws DataAccessException {
        // drop the auths table if it exists
        // create a fresh one
    }

    // Do I want the username or the auth as the primary key?

    public AuthData createAuth(AuthData authData) throws DataAccessException {
        //
        return null;
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        //
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        //
        return null;
    }
}
