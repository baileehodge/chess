package service;

import chess.ChessGame;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import model.*;

import java.util.Collection;

public class GameService {

    private final GameDAO dataAccess;

    public GameService(GameDAO dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Collection<GameData> listGames() throws DataAccessException {
        return dataAccess.listGames();
    }
    public GameData createGame(String gameName) throws DataAccessException {
        return dataAccess.createGame(gameName);
    }
    public GameData joinGame(int gameID) throws DataAccessException {
        return null;
    }
    public GameData updateGame(GameData game) throws DataAccessException {
        return dataAccess.updateGame(game);
    }
}
