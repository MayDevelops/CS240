package Service.Services;

import DataAccessObjects.*;
import Models.AuthToken;
import Models.Person;
import Models.User;
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
        return new PersonResult("Error: Invalid authToken returned null.", false);
      } else {
        if (findPerson == null) {
          db.closeConnection(false);
          return new PersonResult("Error: Person was not found in the database.", false);
        } else if (! findToken.getUsername().equals(findPerson.getAssociatedUsername())) {
          db.closeConnection(false);
          return new PersonResult("Error: Person is not associated with " + findToken.getUsername() + ".", false);
        } else {
          db.closeConnection(true);
          return new PersonResult(findPerson);
        }
      }
    } catch (DataAccessException e) {
      db.closeConnection(false);
      return new PersonResult(e.toString(), false);
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
        return new PersonResult("Error: AuthToken returned null.", false);
      } else {
        ArrayList<Person> persons = personsDAO.FindAll(findToken.getUsername());
        //vvv new

        Person personsPerson = FindPerson(findToken);

        //^^^ new
        if (persons == null) {
          db.closeConnection(false);
          return new PersonResult("Error: " + findToken.getUsername() + " has no associated Persons.", false);
        } else {
          db.closeConnection(true);
          return new PersonResult(persons,personsPerson);
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new PersonResult(e.toString(), false);
    }
  }

  private Person FindPerson(AuthToken t) throws DataAccessException {
    UsersDAO uDAO = new UsersDAO(conn);
    User temp = uDAO.find(t.getUsername());
    return personsDAO.find(temp.getPersonID());
  }


}
