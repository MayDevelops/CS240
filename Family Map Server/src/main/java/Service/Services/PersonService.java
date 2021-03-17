package Service.Services;

import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.DataAccessException;
import DataAccessObjects.DatabaseHead;
import DataAccessObjects.PersonsDAO;
import Models.AuthToken;
import Models.Person;
import Service.Results.*;
import Service.Requests.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class that will handle Person business logic and determine if a request was successful.
 */
public class PersonService {
  private AuthTokenDAO authTokenDAO;
  private PersonsDAO personsDAO;
  private DatabaseHead db;
  private Connection conn;


  public PersonResult Person(String personID, String authToken) throws DataAccessException {
    db = new DatabaseHead();
    conn = db.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    personsDAO = new PersonsDAO(conn);

    try {
      AuthToken findToken = authTokenDAO.find(authToken);
      Person findPerson = personsDAO.find(personID);

      if (findToken == null) {
        db.closeConnection(false);
        return new PersonResult("Error: Invalid authToken returned null.");
      } else {
        if (findPerson == null) {
          db.closeConnection(false);
          return new PersonResult("Error: Person was not found in the database.");
        } else if (! findToken.getUsername().equals(findPerson.getAssociatedUsername())) {
          db.closeConnection(false);
          return new PersonResult("Error: Person is not associated with " + findToken.getUsername() + ".");
        } else {
          db.closeConnection(true);
          return new PersonResult(findPerson);
        }
      }
    } catch (DataAccessException e) {
      db.closeConnection(false);
      return new PersonResult(e.toString());
    }
  }

  public PersonResult Person(String authToken) throws DataAccessException {
    db = new DatabaseHead();
    conn = db.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    personsDAO = new PersonsDAO(conn);
    try {
      AuthToken findToken = authTokenDAO.find(authToken);

      if (findToken == null) {
        db.closeConnection(false);
        return new PersonResult("Error: AuthToken returned null.");
      } else {
        ArrayList<Person> persons = personsDAO.FindAll(findToken.getUsername());
        if (persons == null) {
          db.closeConnection(false);
          return new PersonResult("Error: " + findToken.getUsername() + " has no associated Persons.");
        } else {
          db.closeConnection(true);
          return new PersonResult(persons);
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new PersonResult(e.toString());
    }
  }
}
