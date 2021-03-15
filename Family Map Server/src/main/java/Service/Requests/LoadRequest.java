package Service.Requests;
import Models.*;

/**
 * A class that packages a gson request and turns it into a Java object.
 */
public class LoadRequest {

  private User[] users;
  private Person[] persons;
  private Event[] events;
  private Boolean success;

}
