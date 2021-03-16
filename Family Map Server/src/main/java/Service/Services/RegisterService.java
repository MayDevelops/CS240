package Service.Services;

import DataAccessObjects.*;
import Generators.GenerateData;
import Generators.GenerationStorage;
import Models.AuthToken;
import Models.Event;
import Models.Person;
import Models.User;
import Service.Results.*;
import Service.Requests.*;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Class that will handle Register business logic and determine if a request was successful.
 */
public class RegisterService {

  private RegisterResult errorAccess = new RegisterResult();

  private AuthTokenDAO authTokenDAO;
  private EventsDAO eventsDAO;
  private PersonsDAO personsDAO;
  private UsersDAO usersDAO;

  private AuthToken authToken = new AuthToken();
  private Event event = new Event();
  private Person person = new Person();
  private User user = new User();

  private GenerateData generateData = new GenerateData();

  private int defaultGens = 4;


  /**
   * @param r the GSON information converted to an object.
   * @return the result object or error message.
   */
  public RegisterResult Register(RegisterRequest r) throws DataAccessException {
    DatabaseHead db = new DatabaseHead();
    Connection conn = db.getConnection();

    authTokenDAO = new AuthTokenDAO(conn);
    eventsDAO = new EventsDAO(conn);
    personsDAO = new PersonsDAO(conn);
    usersDAO = new UsersDAO(conn);
    if (! ValidInput(r)) {
      return new RegisterResult("Error: Invalid input.\n");
    }

    CreatePerson(r);
    CreateUser(r);

    try {
      if (usersDAO.find(user.getUsername()) == null) {
        usersDAO.Insert(user);
        authToken = new AuthToken(user.getUsername());
        authTokenDAO.Insert(authToken);

        GenerationStorage generationStorage = generateData.PopulateGenerations(person, defaultGens);

        Insert(generationStorage.getPersonsArray(), generationStorage.getEventsArray());

        db.closeConnection(true);
        return new RegisterResult(authToken.getAuthToken(), user.getUsername(),
                person.getPersonID());
      } else {
        db.closeConnection(false);
        return new RegisterResult("Error: Username is already taken by another user.\n");
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new RegisterResult(e.toString());
    }

  }

  private boolean ValidInput(RegisterRequest r) {
    return ! ((r.getUsername() == null) ||
            (r.getPassword() == null) ||
            (r.getEmail() == null) ||
            (r.getFirstName() == null) ||
            (r.getLastName() == null) ||
            (r.getGender() == null));
  }

  private void CreateUser(RegisterRequest r) {
    user.setUsername(r.getUsername());
    user.setPassword(r.getPassword());
    user.setEmail(r.getEmail());
    user.setFirstName(r.getFirstName());
    user.setLastName(r.getLastName());
    user.setGender(r.getGender());
    user.setPersonID(person.getPersonID());
  }

  private void CreatePerson(RegisterRequest r) {
    person.setAssociatedUsername(r.getUsername());
    person.setFirstName(r.getFirstName());
    person.setLastName(r.getLastName());
    person.setGender(r.getGender());
  }

  private void Insert(ArrayList<Person> persons, ArrayList<Event> events) throws DataAccessException {
    for (int i = 0; i < persons.size(); i++) {
      personsDAO.Insert(persons.get(i));
    }

    for (int i = 0; i < events.size(); i++) {
      eventsDAO.Insert(events.get(i));
    }
  }

}
