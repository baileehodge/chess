package dataAccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO implements GameDAO{

    HashMap<Integer, GameData> games = new HashMap<Integer, GameData>();


    public void clearGames(){
        games.clear();
    }


    public Collection<GameData> listGames(){
        return games.values();
    }


    public GameData createGame(GameData newGame) {
        games.put(newGame.getGameID(),newGame);
        return newGame;
    }

    public GameData getGame(int gameID) {
        return games.get(gameID);
    }

    public void updateGame(GameData game) {
        games.put(game.getGameID(), game);
    }

    @Override
    public String getWhiteUsername(int gameID) {
        return null;
    }

    @Override
    public String getBlackUsername(int gameID) {
        return null;
    }
}

// listGames
// getGame
// createGame
// updateGame

