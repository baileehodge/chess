package dataAccess;

import model.AuthData;
import model.GameData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO{

    // START create db tables on startup if they don't already exist
    public SQLGameDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `id` int NOT NULL AUTO_INCREMENT,
              `gameName` varchar(256),
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              
              PRIMARY KEY (`id`),
              INDEX(gameName)
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
            throw new DataAccessException("Unable to configure game database."); //500
        }
    }

    // END new database creation

    public void clearGames() throws DataAccessException {
        var statement = "TRUNCATE games";
        voidExecute(statement);

    }

    public Collection<GameData> listGames() throws DataAccessException {
        Collection<GameData> games = new ArrayList<>();
        String query = "SELECT * FROM games";
        try (var conn = DatabaseManager.getConnection();
             var ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int gameID = rs.getInt("id");
                String gameName = rs.getString("gameName");
                String whiteUsername = rs.getString("whiteUsername");
                String blackUsername = rs.getString("blackUsername");

                GameData game = new GameData(gameID, whiteUsername, blackUsername, gameName);
                games.add(game);
            }
        } catch (SQLException e) {
            throw new DataAccessException("listGames() error");
        }
        return games;
    }

    public GameData createGame(GameData newGame) throws DataAccessException {
        var statement = "INSERT INTO games (id,gameName) VALUES (?, ?)";
        voidExecute(statement, newGame.getGameID(), newGame.getGameName());
        return newGame;
    }

    public GameData getGame(int gameID) throws DataAccessException {
        if (gameID < 0) {
            throw new DataAccessException("negative gameID");
        }
        var statement = "SELECT * FROM games WHERE id=?";
        return returnExecute(statement, gameID);
    }

    public GameData updateGame(GameData game) throws DataAccessException {
        if (game.getGameID() < 0) {
            throw new DataAccessException("negative gameID");
        }
        var statement = "UPDATE games SET id=?, gameName=?, whiteUsername=?, blackUsername=? WHERE id=?";
        voidExecute(statement, game.getGameID(), game.getGameName(), game.getWhiteUsername(), game.getBlackUsername(),game.getGameID());
        return game;
    }


    private GameData returnExecute(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                for (var i = 0; i < params.length; i++) {
                    var paramG = params[i];
                    switch (paramG) {
                        case String p -> ps.setString(i + 1, p);
                        case Integer p -> ps.setInt(i + 1, p);
                        case null -> ps.setNull(i + 1, NULL);
                        default -> {
                        }
                    }
                }
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Integer gameID = rs.getInt("id");
                    String gameName = rs.getString("gameName");
                    String whiteUsername = rs.getString("whiteUsername");
                    String blackUsername = rs.getString("blackUsername");
                    return new GameData(gameID, whiteUsername, blackUsername, gameName);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return null;
    }
    private void voidExecute(String statement, Object... params) throws DataAccessException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var ps = conn.prepareStatement(statement)) {
                for (var i = 0; i < params.length; i++) {
                    var paramG = params[i];
                    switch (paramG) {
                        case String p -> ps.setString(i + 1, p);
                        case Integer p -> ps.setInt(i + 1, p);
                        case null -> ps.setNull(i + 1, NULL);
                        default -> {
                        }
                    }
                }
                ps.executeUpdate();

            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

}
