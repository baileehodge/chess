package dataAccess;

import model.GameData;

import java.sql.SQLException;
import java.util.Collection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO{

    // START create db tables on startup if they don't already exist
    public SQLGameDAO() throws DataAccessException {
        configureDatabase();
    }

    // TODO: sql unverified
    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `id` int NOT NULL AUTO_INCREMENT,
              `name` varchar(256) NOT NULL,
              `whiteUsername` varchar(256) NOT NULL,
              `blackUsername` varchar(256) NOT NULL,
              
              PRIMARY KEY (`id`),
              INDEX(name),
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
            throw new DataAccessException("Unable to configure game database"); //500
        }
    }

    // END new database creation

    public void clearGames() throws DataAccessException {
        // drop games table if it exists
        // create a fresh one

    }

    public Collection<GameData> listGames() throws DataAccessException {
        // list out the whole table
        return null;
    }

    public GameData createGame(GameData newGame) throws DataAccessException {
        // take the GameData object and make a row in the game table for it
        return null;
    }

    public GameData getGame(int gameID) throws DataAccessException {
        // select from the game table by gameID
        // primary key of the table might need to be gameID
        // return the game as a GameData object
        return null;
    }

    public GameData updateGame(GameData game) throws DataAccessException {
        // UPDATE table
        // get the gameID and new game info from the object and pass it to JDBC
        return null;
    }
}
