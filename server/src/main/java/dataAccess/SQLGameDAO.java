package dataAccess;

import model.GameData;

import java.sql.SQLException;
import java.util.Collection;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class SQLGameDAO implements GameDAO{
    public void clearGames() throws DataAccessException {
        var statement = "TRUNCATE pet";
        // executeUpdate(statement);
        // see prototype
    }

    public Collection<GameData> listGames() throws DataAccessException {
        return null;
    }

    public GameData createGame(GameData newGame) throws DataAccessException {
        return null;
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    public GameData updateGame(GameData game) throws DataAccessException {
        return null;
    }

// prototype, copied from pet shop

//    private int executeUpdate(String statement, Object... params) throws ResponseException {
//        try (var conn = DatabaseManager.getConnection()) {
//            try (var ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
//                for (var i = 0; i < params.length; i++) {
//                    var param = params[i];
//                    if (param instanceof String p) ps.setString(i + 1, p);
//                    else if (param instanceof Integer p) ps.setInt(i + 1, p);
//                    else if (param instanceof PetType p) ps.setString(i + 1, p.toString());
//                    else if (param == null) ps.setNull(i + 1, NULL);
//                }
//                ps.executeUpdate();
//
//                var rs = ps.getGeneratedKeys();
//                if (rs.next()) {
//                    return rs.getInt(1);
//                }
//
//                return 0;
//            }
//        } catch (SQLException e) {
//            throw new ResponseException(500, String.format("unable to update database: %s, %s", statement, e.getMessage()));
//        } catch (DataAccessException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
