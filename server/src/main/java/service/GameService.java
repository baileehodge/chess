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

    public Collection<ChessGame> listGames(UserData user) throws DataAccessException {
        return dataAccess.listGames();
    }
    public GameData createGame(UserData user) throws DataAccessException {
        return dataAccess.createGame();
    }
    public GameData joinGame(UserData user) throws DataAccessException {
        return dataAccess.joinGame();
    }
    public GameData updateGame(UserData user) throws DataAccessException {
        return dataAccess.updateGame();
    }
}
