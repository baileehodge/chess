package service;

import chess.ChessGame;
import dataAccess.*;
import model.*;
import service.requests.JoinRecord;

import java.util.Collection;
import java.util.Objects;
import java.util.Random;


public class GameService {

    private static GameDAO gameAccess = null;
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

    public static boolean verifyGame(int gameID) throws DataAccessException {

        Collection<GameData> listOfGames = gameAccess.listGames();

        for (GameData game : listOfGames) {
            if (game.getGameID() == gameID) {
                return true;
            }
        }
        return false;
    }

    public static ChessGame getGame(int gameID) throws DataAccessException {
        return gameAccess.getGame(gameID).getGame();
    }
    public GameData createGame(String gameName, String authToken) throws DataAccessException, ServiceException {
        if (authAccess.getAuth(authToken) == null) {
            throw new ServiceException("Error: unauthorized");
        }
        Random randomNum = new Random();
        Integer gameID = randomNum.nextInt(1000);
        ChessGame gameObject = new ChessGame();
        GameData newGame = new GameData(gameID, null, null, gameName, gameObject);
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

    public static String getWhiteUsername(int gameID) throws DataAccessException {
        return gameAccess.getWhiteUsername(gameID);
    }

    public static String getBlackUsername(int gameID) throws DataAccessException {
        return gameAccess.getBlackUsername(gameID);
    }
}
