package Service.Results;
/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class EventResult extends  Result {

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
   * The event result that create a response body based off of the success boolean.
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
  public EventResult(Boolean success, String setEventID, String setAssociatedUsername, String setPersonID, String setCountry, String setCity, String setEventType,
               Float setLatitude, Float setLongitude, Integer setYear) {
    if (success) {
      this.setEventID(setEventID);
      this.setAssociatedUsername(setAssociatedUsername);
      this.setPersonID(setPersonID);
      this.setCountry(setCountry);
      this.setCity(setCity);
      this.setEventType(setEventType);
      this.setLatitude(setLatitude);
      this.setLongitude(setLongitude);
      this.setYear(setYear);
      //create success message.
    } else {
      //create error message

    }
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

  public void setEventID(String setEventID) { this.eventID = setEventID; }
  public void setAssociatedUsername(String setAssociatedUsername) { this.associatedUsername = setAssociatedUsername; }
  public void setPersonID(String setPersonID) { this.personID = setPersonID; }
  public void setCountry(String setCountry) { this.country = setCountry; }
  public void setCity(String setCity) { this.city = setCity; }
  public void setEventType(String setEventType) { this.eventType = setEventType; }
  public void setLatitude(Float setLatitude) { this.latitude = setLatitude; }
  public void setLongitude(Float setLongitude) { this.longitude = setLongitude; }
  public void setYear(Integer setYear) { this.year = setYear; }
}



