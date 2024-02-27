package server;

import dataAccess.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ServiceException;
import service.ClearService;

import java.security.Provider;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @BeforeEach
    public void clearAll() throws ServiceException, DataAccessException {
        ClearService.clear();
    }

    @Test
    public void testClear() throws ServiceException, DataAccessException {
        // ad a couple thingies

        assertDoesNotThrow(ClearService::clear);
    }

}