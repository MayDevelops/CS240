package UnitTests;

import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.DataAccessException;
import DataAccessObjects.DatabaseHead;
import Models.AuthToken;
import Service.Services.ClearService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDAOTest {
  DatabaseHead db = new DatabaseHead();
  Connection conn;
  ClearService clearService = new ClearService();
  AuthToken optimusToken;
  AuthToken megatronToken;
  AuthToken bumbleToken;

  AuthTokenDAO aDAO;


  @BeforeEach
  void setUp() throws DataAccessException {
    clearService.ClearDatabase();
    conn = db.getConnection();

    aDAO = new AuthTokenDAO(conn);

    optimusToken = new AuthToken("OptimusPrime", "OppyPoppy1234");
    megatronToken = new AuthToken("Megatron","MeggaBronze4321");
    bumbleToken = new AuthToken("BumbleBee","BumbleRumble9000");

  }

  @AfterEach
  void tearDown() {
    try {
      db.closeConnection(false);
      clearService.ClearDatabase();
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  @Test
  void insertPass() throws DataAccessException, SQLException {
    assertNull(aDAO.find("OppyPoppy1234"));
    assertNull(aDAO.find("MeggaBronze4321"));
    assertNull(aDAO.find("BumbleRumble9000"));

    aDAO.Insert(optimusToken);
    aDAO.Insert(megatronToken);
    aDAO.Insert(bumbleToken);

    conn.commit();

    AuthToken tempOptimus = aDAO.find("OppyPoppy1234");
    AuthToken tempMegatron = aDAO.find("MeggaBronze4321");
    AuthToken tempBumble = aDAO.find("BumbleRumble9000");

    assertNotNull(tempOptimus);
    assertNotNull(tempMegatron);
    assertNotNull(tempBumble);


    assertEquals("OptimusPrime", tempOptimus.getUsername());
    assertEquals("OppyPoppy1234", tempOptimus.getAuthtoken());
    assertEquals("Megatron",tempMegatron.getUsername());
    assertEquals("MeggaBronze4321", tempMegatron.getAuthtoken());
    assertEquals("BumbleBee", tempBumble.getUsername());
    assertEquals("BumbleRumble9000", tempBumble.getAuthtoken());
  }

  @Test
  void insertFail() throws DataAccessException, SQLException {
    assertNull(aDAO.find("OppyPoppy1234"));
    assertNull(aDAO.find("MeggaBronze4321"));
    assertNull(aDAO.find("BumbleRumble9000"));

    aDAO.Insert(optimusToken);
    aDAO.Insert(megatronToken);
    assertThrows(DataAccessException.class, ()-> aDAO.Insert(optimusToken));
    assertThrows(DataAccessException.class, ()-> aDAO.Insert(megatronToken));

    conn.commit();

    AuthToken tempOptimus = aDAO.find("OppyPoppy1234");
    AuthToken tempMegatron = aDAO.find("MeggaBronze4321");

    assertNotNull(tempOptimus);
    assertNotNull(tempMegatron);

    assertEquals("OptimusPrime", tempOptimus.getUsername());
    assertEquals("OppyPoppy1234", tempOptimus.getAuthtoken());
    assertEquals("Megatron",tempMegatron.getUsername());
    assertEquals("MeggaBronze4321", tempMegatron.getAuthtoken());
  }

  @Test
  void findPass() throws DataAccessException, SQLException {
    assertNull(aDAO.find("OppyPoppy1234"));
    assertNull(aDAO.find("MeggaBronze4321"));
    assertNull(aDAO.find("BumbleRumble9000"));

    aDAO.Insert(optimusToken);
    aDAO.Insert(megatronToken);
    aDAO.Insert(bumbleToken);

    conn.commit();

    AuthToken tempOptimus = aDAO.find("OppyPoppy1234");
    AuthToken tempMegatron = aDAO.find("MeggaBronze4321");
    AuthToken tempBumble = aDAO.find("BumbleRumble9000");

    assertNotNull(tempOptimus);
    assertNotNull(tempMegatron);
    assertNotNull(tempBumble);
  }

  @Test
  void findFail() throws DataAccessException, SQLException {
    assertNull(aDAO.find("OppyPoppy1234"));
    assertNull(aDAO.find("MeggaBronze4321"));
    assertNull(aDAO.find("BumbleRumble9000"));

    aDAO.Insert(optimusToken);
    aDAO.Insert(megatronToken);
    aDAO.Insert(bumbleToken);

    conn.commit();

    AuthToken tempOptimus = aDAO.find("OpyPopy123");
    AuthToken tempMegatron = aDAO.find("MegaBronz432");
    AuthToken tempBumble = aDAO.find("BumblRumbl900");

    assertNull(tempOptimus);
    assertNull(tempMegatron);
    assertNull(tempBumble);
  }
}