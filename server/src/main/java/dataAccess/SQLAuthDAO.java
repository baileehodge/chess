package dataAccess;

import model.AuthData;

import java.sql.*;

import static java.sql.Types.NULL;


public class SQLAuthDAO implements AuthDAO{

    // START create db tables on startup if they don't already exist
    public SQLAuthDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auths (
              `auth` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,

              
              PRIMARY KEY (`auth`),
              INDEX(auth),
              INDEX(username)
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
        var statement = "TRUNCATE auths";
        voidExecute(statement);
    }


    public AuthData createAuth(AuthData authData) throws DataAccessException {
        if (authData.getAuthToken().isEmpty()) {
            throw new DataAccessException("missing auth token");
        }
        var statement = "INSERT INTO auths (auth, username) VALUES (?, ?)";
        voidExecute(statement, authData.getAuthToken(), authData.getUsername());
        return authData;
    }

    public void deleteAuth(String authToken) throws DataAccessException {
        if (authToken.isEmpty()) {
            throw new DataAccessException("missing auth token");
        }
        var statement = "DELETE FROM auths WHERE auth=?";
        voidExecute(statement, authToken);
    }

    public AuthData getAuth(String authToken) throws DataAccessException {
        if (authToken == null) {
            throw new DataAccessException("missing auth token");
        }
        var statement = "SELECT * FROM auths WHERE auth=?";
        return returnExecute(statement, authToken);
    }

    private AuthData returnExecute(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                executeHelper(params, ps);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String authToken = rs.getString("auth");
                    String username = rs.getString("username");
                    return new AuthData(username, authToken);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }

    private static void executeHelper(Object[] params, PreparedStatement ps) throws SQLException {
        for (var i = 0; i < params.length; i++) {
            var paramA = params[i];
            switch (paramA) {
                case String p -> ps.setString(i + 1, p);
                case Integer p -> ps.setInt(i + 1, p);
                case null -> ps.setNull(i + 1, NULL);
                default -> {
                }
            }
        }
    }

    private void voidExecute(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                executeHelper(params, ps);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
}
