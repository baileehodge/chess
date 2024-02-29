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


public class Server {

    public GameDAO gameDAO;
    public UserDAO userDAO;
    public AuthDAO authDAO;



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

        Spark.awaitInitialization();
        return Spark.port();
    }

    // the handlers:
    private Object handleRegister(Request request, Response response) {
        return "{}";
    }

    private Object handleLogin(Request request, Response response) {
        return "{}";
    }

    private Object handleLogout(Request request, Response response) {
        return "{}";
    }

    private Object handleNewGame(Request request, Response response) {
        return "{}";
    }

    private Object handleJoinGame(Request request, Response response) {
        return "{}";
    }

    private Object handleListGames(Request request, Response response) throws ServiceException, DataAccessException {
        return "{}";
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
