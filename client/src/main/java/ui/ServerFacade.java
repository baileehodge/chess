package ui;

import com.google.gson.Gson;
import model.*;

import java.io.*;
import java.net.*;

public class ServerFacade {

    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public AuthData register(UserData data) throws UIException {
        var path = "/user";
        return this.makeRequest("POST", path, data, AuthData.class);
    }

    public AuthData login(UserData data) throws UIException {
        var path = "/session";
        return this.makeRequest("POST", path, data, AuthData.class);
    }
    public void logout(AuthData token) throws UIException {
        String auth = token.getAuthToken();
        var path = "/session";
        this.makeRequest("DELETE", path, null, AuthData.class, auth);
    }
    public void createGame(GameData data, String auth) throws UIException {
        var path = "/game";
        this.makeRequest("POST", path, data, GameData.class, auth);
    }
    public void joinGame(String auth, String playerColor, int gameID) throws UIException {
        var path = "/game";
        JoinRecord record = new JoinRecord(auth, playerColor, gameID);
        this.makeRequest("PUT", path, record, GameData.class, auth);
    }
    public GameData[] listGames(String auth) throws UIException {
        var path = "/game";
        record GameList(GameData[] games) {
        }
        var response =  this.makeRequest("GET", path, null, GameList.class, auth);
        return response.games();
    }
    public void clear() throws UIException {
        var path = "/db";
        this.makeRequest("DELETE", path, null, null);
    }





    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws UIException {
        return makeRequest(method, path, request, responseClass, null);
    }


    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String auth) throws UIException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

            if (auth != null) {
                http.addRequestProperty("authorization", auth);
            }

            writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            throw new UIException(500, ex.getMessage());
        }
    }


    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }

    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, UIException {
        var status = http.getResponseCode();
        var error = http.getResponseMessage();
        if (!isSuccessful(status)) {
            throw new UIException(status, "failure: " + status + ", " + error);
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }


    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }
}
