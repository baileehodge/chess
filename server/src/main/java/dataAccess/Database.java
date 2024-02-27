package dataAccess;

import model.*;

import java.util.HashMap;
import java.util.Map;

public class Database {
    static HashMap<String, UserData> users = new HashMap<String, UserData>() {
    };
    static HashMap<String, AuthData> auths = new HashMap<String, AuthData>() {
    };
    static HashMap<Object, GameData> games = new HashMap<Object, GameData>() {
    };

}