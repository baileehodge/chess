package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import dataAccess.MemoryClearDAO;
import service.*;
import spark.Request;
import spark.Response;
import spark.Spark;

import static service.ClearService.clear;

public class Server {

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
        return null;
    }

    private Object handleLogin(Request request, Response response) {
        return null;
    }

    private Object handleLogout(Request request, Response response) {
        return null;
    }

    private Object handleNewGame(Request request, Response response) {
        return null;
    }

    private Object handleJoinGame(Request request, Response response) {
        return null;
    }

    private Object handleListGames(Request request, Response response) {
        return null;
    }

    private Object handleClear(Request request, Response response) {
        try {
            clear();
        } catch (DataAccessException e) {
            response.status(500);
            return new Gson().toJson(new RuntimeException(e.toString()));
        }
        return "";
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
