package Service.Services;

import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.DataAccessException;
import DataAccessObjects.DatabaseHead;
import DataAccessObjects.EventsDAO;
import Models.Event;
import Service.Results.*;
import Models.AuthToken;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Class that will handle Event business logic and determine if a request was successful.
 */
public class EventService {
  private DatabaseHead db;
  private Connection conn;
  private AuthTokenDAO authTokenDAO;
  private EventsDAO eventsDAO;

  /**
   * Function that determines if an event was successful or not.
   *
   * @param r the authtoken passed in to get the event logic for the user.
   * @return the result of the operation.
   */
  public EventResult event(String authToken) throws DataAccessException {
    db = new DatabaseHead();
    conn = db.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    eventsDAO = new EventsDAO(conn);

    try {
      AuthToken findToken = authTokenDAO.find(authToken);
      if (findToken == null) {
        db.closeConnection(false);
        return new EventResult("Error: Invalid authToken returned null.");
      } else {
        ArrayList<Event> events = eventsDAO.findAll(findToken.getUsername());
        if (events == null) {
          db.closeConnection(false);
          return new EventResult("Error: Person is not associated with " + findToken.getUsername() + ".");
        } else {
          db.closeConnection(true);
          return new EventResult(events);
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new EventResult("Error: Database fatal error.");
    }
  }

  public EventResult event(String eventID, String authToken) throws DataAccessException {
    db = new DatabaseHead();
    conn = db.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    eventsDAO = new EventsDAO(conn);

    try {
      AuthToken findToken = authTokenDAO.find(authToken);
      if (findToken == null) {
        db.closeConnection(false);
        return new EventResult("Error: Invalid authToken returned null.");
      } else {
        Event findEvent = eventsDAO.find(eventID);
        if (findEvent == null) {
          db.closeConnection(false);
          return new EventResult("Error: Event does not exist.");
        } else if (! findToken.getUsername().equals(findEvent.getAssociatedUsername())) {
          db.closeConnection(false);
          return new EventResult("Error: Event is not associated with " + findToken.getUsername() + ".");
        } else {
          db.closeConnection(true);
          return new EventResult(findEvent, findToken.getUsername());
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new EventResult("Error: Database fatal error.");
    }
  }
}
