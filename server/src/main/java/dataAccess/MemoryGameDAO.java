package dataAccess;

import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.random;

public class MemoryGameDAO implements GameDAO{

    HashMap<Object, GameData> games = new HashMap<Object, GameData>();

    @Override
    public void clearGames() throws DataAccessException {
        games.clear();
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return games.values();
    }

    @Override
    public GameData createGame(String gameName) throws DataAccessException {
        return null;
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public GameData updateGame(GameData game) throws DataAccessException {
        return null;
    }
}

// listGames
// getGame
// createGame
// updateGame

