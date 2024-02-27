package service;
import dataAccess.*;

public class ClearService {
    // talks to the interfaces

    private static ClearDAO dataAccess = null;


    public ClearService(ClearDAO dataAccess) {
        ClearService.dataAccess = dataAccess;
    }

    public static void clear() throws DataAccessException {
        dataAccess.clear();
        return;
    }
}
