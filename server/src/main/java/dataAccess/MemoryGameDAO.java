package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;

public class MemoryGameDAO implements GameDAO{
    @Override
    public Collection<ChessGame> listGames() throws DataAccessException {
        return null;
    }

    @Override
    public GameData createGame() throws DataAccessException {
        return null;
    }

    @Override
    public GameData joinGame() throws DataAccessException {
        return null;
    }

    @Override
    public GameData updateGame() throws DataAccessException {
        return null;
    }
}

// listGames
// getGame
// createGame
// updateGame

