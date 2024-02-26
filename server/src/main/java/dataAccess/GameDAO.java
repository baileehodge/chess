package dataAccess;
import chess.ChessGame;
import model.GameData;

import javax.xml.crypto.Data;
import java.util.Collection;

public interface GameDAO {

    Collection<ChessGame> listGames() throws DataAccessException;
    GameData createGame() throws DataAccessException;
    GameData joinGame() throws DataAccessException;
    GameData updateGame() throws DataAccessException;
    void clear() throws DataAccessException;
}
