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

    HashMap<Integer, GameData> games = new HashMap<Integer, GameData>();


    public void clearGames() throws DataAccessException {
        games.clear();
    }


    public Collection<GameData> listGames() throws DataAccessException {
        return games.values();
    }


    public GameData createGame(GameData newGame) throws DataAccessException {
        games.put(newGame.getGameID(),newGame);
        return newGame;
    }

    public GameData getGame(int gameID) throws DataAccessException {
        return games.get(gameID);
    }

    public GameData updateGame(GameData game) throws DataAccessException {
        games.put(game.getGameID(), game);
        return game;
    }
}

// listGames
// getGame
// createGame
// updateGame

