package Service.Services;

import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.DataAccessException;
import DataAccessObjects.DatabaseHead;
import DataAccessObjects.UsersDAO;
import Models.AuthToken;
import Models.User;
import Service.Results.*;
import Service.Requests.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class that will handle Login business logic and determine if a request was successful.
 */
public class LoginService {

  private AuthTokenDAO authTokenDAO;
  private UsersDAO usersDAO;
  private Connection conn;

  private DatabaseHead db = new DatabaseHead();
  private AuthToken authToken = new AuthToken();
  private User user = new User();

  public LoginResult login(LoginRequest r) throws DataAccessException, SQLException {
    conn = db.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    usersDAO = new UsersDAO(conn);

    if (! isLoginValid(r)) {
      db.closeConnection(false);
      return new LoginResult("Error: Input is invalid.", false);
    }

    user = usersDAO.find(r.getUsername());

    if (user == null) {
      db.closeConnection(false);
      return new LoginResult("Error: Unable to retrieve requested user, user does not exist.", false);
    } else if (! user.getPassword().equals(r.getPassword())) {
      db.closeConnection(false);
      return new LoginResult("Error: Password is incorrect.", false);
    } else {
      authToken.setUsername(r.getUsername());
      authTokenDAO.Insert(authToken);
      db.closeConnection(true);
      return new LoginResult(authToken.getAuthtoken(), user.getUsername(), user.getPersonID());
    }
  }

  private boolean isLoginValid(LoginRequest r) {
    return ! (r.getPassword() == null || r.getUsername() == null);
  }
}

