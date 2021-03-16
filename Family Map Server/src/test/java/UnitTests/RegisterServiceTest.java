package UnitTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.DatabaseHead;
import Service.Requests.RegisterRequest;
import Service.Results.RegisterResult;
import Service.Services.ClearService;
import Service.Services.RegisterService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

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
  public void registerNewUser() throws DataAccessException {
    RegisterService registerService = new RegisterService();
    RegisterRequest registerRequest = new RegisterRequest("username", "password", "email", "firstname",
            "lastname", "M");

    RegisterResult registerResult = registerService.Register(registerRequest);

    Assert.assertNotNull(registerResult.getPersonID());
    Assert.assertNotNull(registerResult.getAuthToken());
    Assert.assertNotNull(registerResult.getUsername());
    Assert.assertNull(registerResult.getErrorMessage());
    clearService.ClearDatabase();
  }

  @Test
  public void registerNewUserInvalidInput() throws DataAccessException {
    RegisterService registerService = new RegisterService();
    RegisterRequest registerRequest = new RegisterRequest("A", "B", "C", "D",
            "E", "F");
    registerRequest.setGender(null);

    RegisterResult registerResult = registerService.Register(registerRequest);

    Assert.assertNotNull(registerResult.getErrorMessage());
    Assert.assertEquals(registerResult.getErrorMessage(), "Error: Invalid input.\n");
    Assert.assertNull(registerResult.getAuthToken());
    Assert.assertNull(registerResult.getUsername());
    Assert.assertNull(registerResult.getPersonID());
  }

  @Test
  public void registerNewUserAlreadyExists() throws DataAccessException {

    RegisterService registerService = new RegisterService();
    RegisterRequest registerRequest = new RegisterRequest("A", "B", "C", "D",
            "E", "F");
    RegisterResult registerResult = registerService.Register(registerRequest);

    registerResult = registerService.Register(registerRequest);

    Assert.assertNotNull(registerResult.getErrorMessage());
    Assert.assertEquals(registerResult.getErrorMessage(), "Error: Username is already taken by another user.\n");
    Assert.assertNull(registerResult.getAuthToken());
    Assert.assertNull(registerResult.getUsername());
    Assert.assertNull(registerResult.getPersonID());

    clearService.ClearDatabase();
  }
}