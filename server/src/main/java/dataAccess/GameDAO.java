package dataAccess;
import chess.ChessGame;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;

public interface GameDAO {

    Collection<GameData> listGames() throws DataAccessException;
    GameData createGame(String gameName) throws DataAccessException;
    GameData joinGame(int gameID) throws DataAccessException;
    GameData updateGame(GameData game) throws DataAccessException;

}
