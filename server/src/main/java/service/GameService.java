package service;

import chess.ChessGame;
import dataAccess.*;
import model.*;

import java.util.Collection;
import java.util.Objects;


public class GameService {

    private final GameDAO gameAccess;
    private final AuthDAO authAccess;

    public GameService(GameDAO gameAccess, AuthDAO authAccess) {
        
        this.gameAccess = gameAccess;
        this.authAccess = authAccess;
    }

    public Collection<GameData> listGames(String authToken) throws ServiceException, DataAccessException {
        if (authAccess.getAuth(authToken) == null) {
            throw new ServiceException("Error: unauthorized");
        }
        return gameAccess.listGames();
    }
    public GameData createGame(String gameName, String authToken) throws DataAccessException, ServiceException {
        if (authAccess.getAuth(authToken) == null) {
            throw new ServiceException("Error: unauthorized");
        }
        return gameAccess.createGame(gameName);
    }
    public void joinGame(int gameID, String authToken, String color) throws DataAccessException, ServiceException {
        if (authAccess.getAuth(authToken) == null) {
            throw new ServiceException("Error: unauthorized");
        }
        GameData game = gameAccess.getGame(gameID);
        String username = (authAccess.getAuth(authToken).getUsername());

        if (game == null) {
            throw new ServiceException("Error: bad request");
        }
        if (Objects.equals(color, "BLACK")) {
            if (game.getBlackUsername() == null) {
                game.setBlackUsername(username);
            }
            else {
                throw new ServiceException("Error: already taken");
            }
        }
        if (Objects.equals(color, "WHITE")) {
            if (game.getWhiteUsername() == null) {
                game.setWhiteUsername(username);
            }
            else {
                throw new ServiceException("Error: already taken");
            }
        }
    }
}
