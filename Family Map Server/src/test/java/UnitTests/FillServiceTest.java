package UnitTests;

import Models.AuthToken;
import Service.Results.FillResult;
import Service.Results.PersonResult;
import Service.Services.ClearService;
import DataAccessObjects.*;
import Models.User;
import Service.Services.FillService;
import Service.Services.PersonService;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
  DatabaseHead db = new DatabaseHead();
  ClearService clearService = new ClearService();
  Connection conn;


  @BeforeEach
  public void setUp() throws SQLException, DataAccessException {
    ClearService clearService = new ClearService();
    clearService.ClearDatabase();
    conn = db.getConnection();

    AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);
    UsersDAO usersDAO = new UsersDAO(conn);

    User tempUser = new User("OptimusPrime", "autobots", "stars@yahoo",
            "Optimus", "Prime", "R", "OppyPoppy");

    authTokenDAO.Insert(new AuthToken("OptimusPrime", "1111"));
    authTokenDAO.Insert(new AuthToken("Megatron", "2222"));
    usersDAO.Insert(tempUser);

    conn.commit();
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    clearService.ClearDatabase();
    db.closeConnection(false);
  }

  @Test
  public void fill() throws DataAccessException {
    PersonService personService = new PersonService();
    PersonResult personResult = personService.Person("1111");

    assertNull(personResult.getPersons());
    assertEquals("Error: OptimusPrime has no associated Persons.", personResult.getMessage());

    FillService fillService = new FillService();
    FillResult fillResult = fillService.Fill("OptimusPrime", 4);

    personResult = personService.Person("1111");
    assertNotNull(personResult.getPersons());
    assertEquals(personResult.getPersons().size(), 31);
    assertNotEquals("User was not found.", fillResult.getMessage());
    assertNotEquals("Number of generations is not valid.", fillResult.getMessage());
    assertNotEquals("FatalError", fillResult.getMessage());
  }

  @Test
  public void fillFail() throws DataAccessException {
    PersonService personService = new PersonService();
    PersonResult personResult = personService.Person("2222");
    assertNull(personResult.getPersons());
    assertEquals("Error: Megatron has no associated Persons.",personResult.getMessage());

    FillService fillService = new FillService();
    FillResult fillResult = fillService.Fill("Megatron", - 1);

    assertEquals("Error: Number of generations is less than or equal to 0.", fillResult.getMessage());
  }
}
