package Service.Services;

import DataAccessObjects.*;
import Generators.GenerateData;
import Generators.GenerationStorage;
import Models.Event;
import Models.Person;
import Models.User;
import Service.Results.*;
import Service.Requests.*;

import javax.annotation.processing.Generated;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Class that will handle Fill business logic and determine if a request was successful.
 */
public class FillService {
  private EventsDAO eventsDAO;
  private PersonsDAO personsDAO;
  private UsersDAO usersDAO;
  private DatabaseHead db;
  private Connection conn;

  public FillResult Fill(String username, int numGen) throws DataAccessException {
    GenerateData generateData = new GenerateData();
    db = new DatabaseHead();
    conn = db.getConnection();
    eventsDAO = new EventsDAO(conn);
    personsDAO = new PersonsDAO(conn);
    usersDAO = new UsersDAO(conn);

    if (numGen <= 0) {
      db.closeConnection(false);
      return new FillResult("Error: Number of generations is less than or equal to 0.");
    }

    try {
      if (usersDAO.find(username) == null) {
        db.closeConnection(false);
        return new FillResult("Error: User does not exist.");
      } else if (! ClearUsersInfo(username)) {
        db.closeConnection(false);
        return new FillResult("Error: Failed to delete " + username + " Events and Persons" +
                " information from the database.");
      } else {
        Person temp = UserToPerson(usersDAO.find(username));
        GenerationStorage generationStorage = generateData.PopulateGenerations(temp, numGen);
        Insert(generationStorage.getPersonsArray(), generationStorage.getEventsArray());
        db.closeConnection(true);
        return new FillResult("Successfully added " + generationStorage.getPersonsArray().size() +
                " Persons and " + generationStorage.getEventsArray().size() + " Events.");
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
    }
    db.closeConnection(false);
    return new FillResult("Error: Fatal fill error.");
  }

  private boolean ClearUsersInfo(String username) {

    boolean success = false;

    if (eventsDAO.Delete(username) && personsDAO.Delete(username)) {
      success = true;
    }

    return success;
  }

  private Person UserToPerson(User user) {
    Person p = new Person();

    p.setAssociatedUsername(user.getUsername());
    p.setFirstName(user.getFirstName());
    p.setLastName(user.getLastName());
    p.setGender(user.getGender());

    return p;
  }

  private void Insert(ArrayList<Person> persons, ArrayList<Event> events) throws DataAccessException {
    if (persons.size() == 0) { throw new DataAccessException("Error: Persons array is empty."); }
    if (events.size() == 0) { throw new DataAccessException("Error: Events array is empty."); }
    for (Person temp : persons) { personsDAO.Insert(temp); }
    for (Event temp : events) { eventsDAO.Insert(temp); }
  }
}
