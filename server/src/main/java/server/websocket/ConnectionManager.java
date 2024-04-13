package server.websocket;

import WebSocketMessages.serverMessages.ServerMessage;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectionManager {
    //
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();
    public final ConcurrentHashMap<Connection, Integer> games = new ConcurrentHashMap<Connection, Integer>();

    public void add(String visitorName, Session session, int gameID) {
        var connection = new Connection(visitorName, session);
        connections.put(visitorName, connection);

        // should I make gameID an Integer before I add it to the hash map?
        games.put(connection, gameID);
    }

    public void remove(String visitorName) {
        var connection = connections.get(visitorName);

        // if the homie even exists
        if (connection != null) {
            connections.remove(visitorName);
            games.remove(connection);
        }

    }

    // It's called gossip because it notifies all the users except for one lol
    public void gossip(String excludeVisitorName, ServerMessage notification) throws IOException {
        var removeList = new ArrayList<Connection>();
        for (var c : connections.values()) {
            if (c.session.isOpen()) {
                if (!c.visitorName.equals(excludeVisitorName)) {
                    c.send(notification.toString());
                }
            } else {
                removeList.add(c);
            }
        }

        // Clean up any connections that were left open.
        for (var c : removeList) {
            connections.remove(c.visitorName);
        }
    }

    // tell everyone
    public void announce(int gameID, ServerMessage notification) throws IOException {
        String message = new Gson().toJson(notification);

        for (var c : games.entrySet()) {
            if (c.getKey().session.isOpen()) {
                if (c.getValue().equals(gameID)) {
                    c.getKey().send(message);
                }
            }
        }
    }
}
