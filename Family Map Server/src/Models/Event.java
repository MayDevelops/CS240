/**
 * The event class represents important events from the persons life. These events contain information like the
 * unique event identification tag, the username that the event is associated with,  the country and city where it took place,
 * the latitude and longitude of where it took place, the type of event that took place, and the year that it took place.
 */
public class Event {

  private String eventID;
  private String associatedUsername;
  private String personID;
  private String country;
  private String city;
  private String eventType;
  private Float latitude;
  private Float longitude;
  private Integer year;

  /**
   * The default constructor that initializes all of the local variables to null.
   */
  public Event() {
    eventID = null;
    associatedUsername = null;
    personID = null;
    country = null;
    city = null;
    eventType = null;
    latitude = null;
    longitude = null;
    year = null;
  }

  /**
   * The event pojo that represents an event that takes place in a persons life.
   * @param setEventID the unique identifier for the event.
   * @param setAssociatedUsername the user's username that is tied to this event.
   * @param setPersonID the user's ID that this event is tied to.
   * @param setCountry the country where the event took place.
   * @param setCity the city where the event took place.
   * @param setEventType the type of event that happened (birth, baptism, christening, marriage, death, etc.).
   * @param setLatitude the latitude of event's location.
   * @param setLongitude the longitude of event's location.
   * @param setYear the year the event took place.
   */
  public Event(String setEventID, String setAssociatedUsername, String setPersonID, String setCountry, String setCity, String setEventType,
               Float setLatitude, Float setLongitude, Integer setYear) {
    this.eventID = setEventID;
    this.associatedUsername = setAssociatedUsername;
    this.personID = setPersonID;
    this.country = setCountry;
    this.city = setCity;
    this.eventType = setEventType;
    this.latitude = setLatitude;
    this.longitude = setLongitude;
    this.year = setYear;
  }

  public String getEventID() { return eventID; }
  public String getAssociatedUsername() { return associatedUsername; }
  public String getPersonID() { return personID; }
  public String getCountry() { return country; }
  public String getCity() { return city; }
  public String getEventType() { return eventType; }
  public Float getLatitude() { return latitude; }
  public Float getLongitude() { return longitude; }
  public Integer getYear() { return year; }

  public void setEventID(String eventID) { this.eventID = eventID; }
  public void setAssociatedUsername(String associatedUsername) { this.associatedUsername = associatedUsername; }
  public void setCountry(String country) { this.country = country; }
  public void setCity(String city) { this.city = city; }
  public void setEventType(String eventType) { this.eventType = eventType; }
  public void setLatitude(Float latitude) { this.latitude = latitude; }
  public void setLongitude(Float longitude) { this.longitude = longitude; }
  public void setYear(Integer year) { this.year = year; }
}
