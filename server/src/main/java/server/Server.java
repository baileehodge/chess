package server;

import com.google.gson.Gson;
import dataAccess.*;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.websocket.WebSocketHandler;
import service.*;
import service.requests.JoinRecord;
import service.requests.MyError;
import spark.Request;
import spark.Response;
import spark.Spark;

import model.*;

import java.util.Map;

@WebSocket
public class Server {

    public SQLGameDAO gameDAO = new SQLGameDAO();
    public SQLUserDAO userDAO = new SQLUserDAO();
    public SQLAuthDAO authDAO = new SQLAuthDAO();

    public AuthService authService = new AuthService(authDAO);
    public GameService gameService = new GameService(gameDAO, authDAO);
    public UserService userService = new UserService(userDAO, authDAO, authService);
    public ClearService clearService = new ClearService(userDAO,authDAO,gameDAO);

    private final WebSocketHandler wsHandler = new WebSocketHandler();

    public Server() {
    }

    // not functional?
    public void main() {
        Server server = new Server();
        server.run(8080);
        Spark.webSocket("/connect", Server.class);
        Spark.get("/echo/:msg", (req, res) -> "HTTP response: " + req.params(":msg")); // CLIENT RECEIVE?
        System.out.println("SERVER RECEIVE");

    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        session.getRemote().sendString("WebSocket response: " + message);
        System.out.println("SERVER SEND");
    }


    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        Spark.webSocket("/connect", wsHandler);

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
        MyError error;
        switch (ex.getMessage()) {
            case "Error: bad request":
                res.status(400);
                error = new MyError("Error: bad request");
                break;
            case "Error: unauthorized":
                res.status(401);
                error = new MyError("Error: unauthorized");
                break;
            case "Error: already taken":
                res.status(403);
                error = new MyError("Error: already taken");
                break;
            default:
                res.status(500);
                error = new MyError("Error: bad request");
                break;
        }
        res.body(new Gson().toJson(error));
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

    private Object handleLogout(Request request, Response response) throws DataAccessException, ServiceException {
        String authToken = request.headers("Authorization");
        userService.logout(authToken);
        response.status(200);
        return "{}";
    }

    private Object handleNewGame(Request request, Response response) throws ServiceException, DataAccessException {
        String authToken = request.headers("Authorization");
        GameData gameData = new Gson().fromJson(request.body(), GameData.class);
        var game = gameService.createGame(gameData.getGameName(), authToken);
        response.status(200);
        return new Gson().toJson(game);
    }

    private Object handleJoinGame(Request request, Response response) throws ServiceException, DataAccessException {
        String authToken = request.headers("Authorization");
        JoinRecord joinRecord = new Gson().fromJson(request.body(), JoinRecord.class);
        joinRecord = new JoinRecord(authToken, joinRecord.playerColor(), joinRecord.gameID());
        if (joinRecord.gameID() == null) {
            throw new ServiceException("Error: bad request");
        }
        gameService.joinGame(joinRecord);
        response.status(200);
        return "{}";
    }

    private Object handleListGames(Request request, Response response) throws ServiceException, DataAccessException {
        response.type("application/json");
        String authToken = request.headers("Authorization");
        var list = gameService.listGames(authToken).toArray();
        response.status(200);
        return new Gson().toJson(Map.of("games", list));
    }

    private Object handleClear(Request request, Response response) throws DataAccessException {
        clearService.clearAll();
        response.status(200);
        return "{}";
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
