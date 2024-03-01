package server;

import com.google.gson.Gson;
import dataAccess.*;
import service.*;
import spark.Request;
import spark.Response;
import spark.Spark;

import static service.ClearService.clearAll;

import spark.Request;
import spark.Response;
import model.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Server {

    public MemoryGameDAO gameDAO = new MemoryGameDAO();
    public MemoryUserDAO userDAO = new MemoryUserDAO();
    public MemoryAuthDAO authDAO = new MemoryAuthDAO();

    public GameService gameService = new GameService(gameDAO, authDAO);
    public UserService userService = new UserService(userDAO, authDAO);



    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", this::handleRegister);
        Spark.post("/session", this::handleLogin);
        Spark.delete("/session", this::handleLogout);
        Spark.post("/game", this::handleNewGame);
        Spark.put("/game", this::handleJoinGame);
        Spark.get("/game", this::handleListGames);
        Spark.delete("/db", this::handleClear);
        Spark.exception(Exception.class, this::exceptionHandler);

        Spark.awaitInitialization();
        return Spark.port();
    }

    // the handlers:
    private void exceptionHandler(Exception ex, Request req, Response res) {
        res.status(401);
        switch (ex.getMessage()) {
            case "Error: bad request":
                res.status(400);
            case "Error: unauthorized":
                res.status(401);
                break;
            case "Error: already taken":
                res.status(403);
            default:
                res.status(500);
        }
        res.body(new Gson().toJson(Map.of("message", ex.getMessage())));
    }

    private Object handleRegister(Request request, Response response) throws ServiceException, DataAccessException {
        UserData userData = new Gson().fromJson(request.body(), UserData.class);
        var user = userService.register(userData);
        response.status(200);
        return new Gson().toJson(user);
    }

    private Object handleLogin(Request request, Response response) throws ServiceException, DataAccessException {
        UserData userData = new Gson().fromJson(request.body(), UserData.class);
        var auth = userService.login(userData);
        response.status(200);
        return new Gson().toJson(auth);
    }

    private Object handleLogout(Request request, Response response) throws DataAccessException {
        UserData userData = new Gson().fromJson(request.body(), UserData.class);
        userService.logout(userData);
        response.status(200);
        response.body("beep beep");
        return response;
    }

    private Object handleNewGame(Request request, Response response) throws ServiceException, DataAccessException {
        AuthData authData = new Gson().fromJson(request.body(), AuthData.class);
        GameData gameData = new Gson().fromJson(request.body(), GameData.class);
        var game = gameService.createGame(gameData.getGameName(), authData.getAuthToken());
        response.status(200);
        return new Gson().toJson(game);
    }

    private Object handleJoinGame(Request request, Response response) throws ServiceException, DataAccessException {
        AuthData authData = new Gson().fromJson(request.body(), AuthData.class);
        GameData gameData = new Gson().fromJson(request.body(), GameData.class);
        String color = "GREEN";
        if (Objects.equals(authData.getUsername(), gameData.getBlackUsername())) {
            color = "BLACK";
        }
        else if (Objects.equals(authData.getUsername(), gameData.getWhiteUsername())) {
            color = "WHITE";
        }
        if (gameData.getGameID() == null) {
            response.status(403);
            response.body();
        }
        gameService.joinGame(gameData.getGameID(), authData.getAuthToken(), color);
        response.status(200);
        return "{}";
    }

    private Object handleListGames(Request request, Response response) throws ServiceException, DataAccessException {
        response.type("application/json");
        String authToken = request.headers("Authorization");
        var list = gameService.listGames(authToken).toArray(); // supposed to get auth token from request?
        response.status(200);
        return new Gson().toJson(Map.of("games", list));
    }

    private Object handleClear(Request request, Response response) throws DataAccessException {
        clearAll();
        response.status(200);
        return "{}";
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
