package UnitTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.DatabaseHead;
import Service.Requests.RegisterRequest;
import Service.Results.RegisterResult;
import Service.Services.ClearService;
import Service.Services.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
  DatabaseHead db = new DatabaseHead();
  ClearService clearService = new ClearService();

  @BeforeEach
  public void setUp() throws DataAccessException {
    clearService.ClearDatabase();
    Connection conn = db.getConnection();
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    db.closeConnection(false);
  }

  @Test
  public void RegisterPass() throws DataAccessException {
    RegisterService registerService = new RegisterService();
    RegisterRequest registerRequest = new RegisterRequest("username", "password", "email", "firstname",
            "lastname", "M");

    RegisterResult registerResult = registerService.Register(registerRequest);

    assertNotNull(registerResult.getPersonID());
    assertNotNull(registerResult.getAuthToken());
    assertNotNull(registerResult.getUsername());
    assertNull(registerResult.getMessage());
    clearService.ClearDatabase();
  }

  @Test
  public void RegisterFail() throws DataAccessException {
    RegisterService registerService = new RegisterService();
    RegisterRequest registerRequest = new RegisterRequest("A", "B", "C", "D",
            "E", "F");
    registerRequest.setGender(null);

    RegisterResult registerResult = registerService.Register(registerRequest);

    assertNotNull(registerResult.getMessage());
    assertEquals("Error: Invalid input.", registerResult.getMessage());
    assertNull(registerResult.getAuthToken());
    assertNull(registerResult.getUsername());
    assertNull(registerResult.getPersonID());
  }

  @Test
  public void ExistingUserPass() throws DataAccessException {

    RegisterService registerService = new RegisterService();
    RegisterRequest registerRequest = new RegisterRequest("A", "B", "C", "D",
            "E", "F");
    RegisterResult registerResult = registerService.Register(registerRequest);

    registerResult = registerService.Register(registerRequest);

    assertNotNull(registerResult.getMessage());
    assertEquals("Error: Username is already taken by another user.", registerResult.getMessage());
    assertNull(registerResult.getAuthToken());
    assertNull(registerResult.getUsername());
    assertNull(registerResult.getPersonID());

    clearService.ClearDatabase();
  }
}