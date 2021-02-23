/**
 * A class used for accessing the Event table in the database.
 */
public class EventsDAO {

  private String associatedUsername;
  private String eventID;
  private String personID;
  private String country;
  private String city;
  private String eventType;
  private Float latitude;
  private Float longitude;
  private Integer year;

  /**
   * Inserts the event into the Event table in the database.
   * @param event the event to be inserted into the database.
   * @return true or false depending on if the event is correctly inserted into the table.
   */
  public Boolean Insert(Event event) { return null; }

  /**
   * Deletes an event from the Event table in the database.
   * @param event the event to be deleted.
   * @return true or false depending on if the User object is correctly removed from the table.
   */
  public Boolean Delete(Event event) { return null; }

  /**
   * Clears the Event table in the database.
   * @return true or false depending on if the table is cleared correctly.
   */
  public Boolean Clear(Event event) { return null; }

  public String getEventID(Event event) { return event.getEventID(); }
  public String getAssociatedUsername(Event event) { return event.getAssociatedUsername(); }
  public String getPersonID(Event event) { return event.getPersonID(); }
  public String getCountry(Event event) { return event.getCountry(); }
  public String getCity(Event event) { return event.getCity(); }
  public String getEventType(Event event) { return event.getEventType(); }
  public Float getLatitude(Event event) { return event.getLatitude(); }
  public Float getLongitude(Event event) { return event.getLongitude(); }
  public Integer getYear(Event event) { return event.getYear(); }

  public void setEventID(String eventID, Event setEvent) { setEvent.setEventID(eventID); }
  public void setAssociatedUsername(String setAssociatedUsername, Event event) { event.setAssociatedUsername(setAssociatedUsername); }
  public void setCountry(String setCountry, Event event) { event.setCountry(setCountry); }
  public void setCity(String setCity, Event event) { event.setCity(setCity); }
  public void setEventType(String setEventType, Event event) { event.setEventType(setEventType); }
  public void setLatitude(Float setLatitude, Event event) { event.setLatitude(setLatitude); }
  public void setLongitude(Float setLongitude, Event event) { event.setLongitude(setLongitude); }
  public void setYear(Integer setYear, Event event) { event.setYear(setYear); }




}
