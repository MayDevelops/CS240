package Service.Requests;
import Models.*;
/**
 * A class that packages a gson request and turns it into a Java object.
 */
public class EventRequest {

  private Event[] events;
  private Boolean success;
  private String eventID;
  private String associatedUsername;
  private String personID;
  private String country;
  private String city;
  private String eventType;
  private Float latitude;
  private Float longitude;
  private Integer year;

}
