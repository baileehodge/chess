package service;

import chess.ChessGame;
import dataAccess.*;
import model.*;
import service.requests.JoinRecord;

import java.util.Collection;
import java.util.Objects;
import java.util.Random;


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
        Random randomNum = new Random();
        Integer gameID = randomNum.nextInt(1000);
        GameData newGame = new GameData(gameID, null, null, gameName);
        return gameAccess.createGame(newGame);
    }
    public void joinGame(JoinRecord joinRecord) throws DataAccessException, ServiceException {
        if (authAccess.getAuth(joinRecord.authToken()) == null) {
            throw new ServiceException("Error: unauthorized");
        }
        GameData game = gameAccess.getGame(joinRecord.gameID());
        if (game == null) {
            throw new ServiceException("Error: bad request");
        }
        String username = (authAccess.getAuth(joinRecord.authToken()).getUsername());

        if (joinRecord.playerColor() == null) {
            // for websockets
        }
        else if ((joinRecord.playerColor().equalsIgnoreCase("BLACK"))) {
            if (game.getBlackUsername() == null) {
                game.setBlackUsername(username);
                gameAccess.updateGame(game);
            }
            else {
                throw new ServiceException("Error: already taken");
            }
        }
        else if ((joinRecord.playerColor().equalsIgnoreCase("WHITE"))) {
            if (game.getWhiteUsername() == null) {
                game.setWhiteUsername(username);
                gameAccess.updateGame(game);
            }
            else {
                throw new ServiceException("Error: already taken");
            }
        }
        else {
            throw new ServiceException("Error: bad request");
        }
    }

    // new methods, phase 6

    void joinPlayer() {

    }
    void joinObserver() {

    }
    void makeMove() {

    }
    void leaveGame() {

    }
    void resignGame() {

    }


}
