package dataAccess;

import model.*;

import java.util.HashMap;
import java.util.Map;

public class Database {
    static Map<String, UserData> users = new HashMap<String, UserData>() {
    };
    static Map<String, UserData> auths = new HashMap<String, UserData>() {
    };
    static Map<Integer, UserData> games = new HashMap<Integer, UserData>() {
    };

}