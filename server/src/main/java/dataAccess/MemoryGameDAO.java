package dataAccess;

import chess.ChessGame;
import model.GameData;

import java.util.Collection;
import java.util.Random;

import static dataAccess.Database.games;
import static java.lang.Math.random;

public class MemoryGameDAO implements GameDAO{
    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return games.values();
    }

    @Override
    public GameData createGame(String gameName) throws DataAccessException {
        Random random = new Random();
        int randomNumber = random.nextInt(1000);

        GameData game = new GameData();
        game.setGameID(randomNumber);
        return game;
    }

    @Override
    public GameData joinGame(int gameID) throws DataAccessException {
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

