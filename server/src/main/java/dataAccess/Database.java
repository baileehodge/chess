package dataAccess;

import model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Database {
    Map<String, UserData> users = new HashMap<String, UserData>() {};
    Map<String, UserData> auths = new HashMap<String, UserData>() {};
    Map<Integer, UserData> games = new HashMap<Integer, UserData>() {};

    public void clear() {
        users = new HashMap<String, UserData>() {};
        auths = new HashMap<String, UserData>() {};
        games = new HashMap<Integer, UserData>() {};

        return;
    }










    // I think that it doesn't make sense to have these here at all...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Database database = (Database) o;
        return Objects.equals(users, database.users) && Objects.equals(auths, database.auths) && Objects.equals(games, database.games);
    }

    @Override
    public int hashCode() {
        return Objects.hash(users, auths, games);
    }
}
