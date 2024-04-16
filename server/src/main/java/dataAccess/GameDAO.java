package dataAccess;
import model.GameData;

import java.util.Collection;

public interface GameDAO {

    void clearGames() throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;
    GameData createGame(GameData newGame) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    void updateGame(GameData game) throws DataAccessException;

    String getWhiteUsername(int gameID) throws DataAccessException;
    String getBlackUsername(int gameID) throws DataAccessException;

}
