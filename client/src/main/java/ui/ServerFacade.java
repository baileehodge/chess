package ui;

import com.google.gson.Gson;
import ui.UIException;
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

    public Object login(UserData data) throws UIException {
        var path = "/session";
        return this.makeRequest("POST", path, data, AuthData.class);
    }
    public Object logout(AuthData data) throws UIException {
        var path = "/session";
        return this.makeRequest("DELETE", path, data, AuthData.class);
    }
    public Object createGame(GameData data) throws UIException {
        var path = "/game";
        return this.makeRequest("POST", path, data, GameData.class);
    }
    public Object joinGame(GameData data) throws UIException {
        var path = "/game";
        return this.makeRequest("PUT", path, data, GameData.class);
    }
    public Object listGames(GameData data) throws UIException {
        var path = "/game";
        return this.makeRequest("GET", path, data, GameData.class);
    }
    public Object clear() throws UIException {
        var path = "/db";
        return this.makeRequest("DELETE", path, null, null);
    }







    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) throws UIException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setDoOutput(true);

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
        if (!isSuccessful(status)) {
            throw new UIException(status, "failure: " + status);
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
