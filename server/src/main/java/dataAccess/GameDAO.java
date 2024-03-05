package dataAccess;
import chess.ChessGame;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface GameDAO {

    void clearGames() throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;
    GameData createGame(GameData newGame) throws DataAccessException;
    GameData getGame(int gameID) throws DataAccessException;
    GameData updateGame(GameData game) throws DataAccessException;

}
