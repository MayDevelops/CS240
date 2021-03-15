package Service.Results;
import Models.*;

/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class PersonResult extends  Result {

  private Person[] persons;
  private String personID;
  private String associatedUsername;
  private String firstName;
  private String lastName;
  private String gender;
  private String fatherID;
  private String motherID;
  private String spouseID;

  /**
   * Constructor that does accept the spouseID and all of the other data members needed to create the response body.
   * @param success determines if the result is success or failure.
   * @param setPersonID the person's unique ID
   * @param setAssociatedUsername the username that the user is using
   * @param setFirstName the person's first name
   * @param setLastName the person's last name
   * @param setGender the person's gender
   * @param setFatherID the person's father's unique ID
   * @param setMotherID the person's mother's unique ID
   * @param setSpouseID the person's spouse's unique ID if they are married
   */
  public PersonResult(Boolean success, String setPersonID, String setAssociatedUsername, String setFirstName, String setLastName,
                      String setGender, String setFatherID, String setMotherID, String setSpouseID) {
    if(success) {
      this.setPersonID(setPersonID);
      this.setAssociatedUsername(setAssociatedUsername);
      this.setFirstName(setFirstName);
      this.setLastName(setLastName);
      this.setGender(setGender);
      this.setFatherID(setFatherID);
      this.setMotherID(setMotherID);
      this.setSpouseID(setSpouseID);
      //create success message
    } else {
      //create error message
    }

  }

  /**
   * Constructor that does not accept the spouseID, meaning that their is no spouseID associated with this person.
   * @param success determines if the result is success or failure.
   * @param setPersonID the person's unique ID
   * @param setAssociatedUsername the username that the user is using
   * @param setFirstName the person's first name
   * @param setLastName the person's last name
   * @param setGender the person's gender
   * @param setFatherID the person's father's unique ID
   * @param setMotherID the person's mother's unique ID
   */
  public PersonResult(Boolean success, String setPersonID, String setAssociatedUsername, String setFirstName, String setLastName,
                      String setGender, String setFatherID, String setMotherID) {
    if(success) {
      this.setPersonID(setPersonID);
      this.setAssociatedUsername(setAssociatedUsername);
      this.setFirstName(setFirstName);
      this.setLastName(setLastName);
      this.setGender(setGender);
      this.setFatherID(setFatherID);
      this.setMotherID(setMotherID);
      //create success message
    } else {
      //create error message
    }
  }

  /**
   * Constructor that gets all of the Person objects.
   * @param success determines if the result is success or failure.
   */
  public PersonResult(Boolean success) {
    if(success) {
      //create success message
      //display data array
    } else {
      //create error message
    }
  }

  public String getPersonID() { return personID; }
  public String getAssociatedUsername() { return associatedUsername; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public String getGender() { return gender; }
  public String getFatherID() { return fatherID; }
  public String getMotherID() { return motherID; }
  public String getSpouseID() { return spouseID; }

  public void setPersonID(String setPersonID) { this.personID = setPersonID; }
  public void setAssociatedUsername(String setAssociatedUsername) { this.associatedUsername = setAssociatedUsername; }
  public void setFirstName(String setFirstName) { this.firstName = setFirstName; }
  public void setLastName(String setLastName) { this.lastName = setLastName; }
  public void setGender(String setGender) { this.gender = setGender; }
  public void setFatherID(String setFatherID) { this.fatherID = setFatherID; }
  public void setMotherID(String setMotherID) { this.motherID = setMotherID; }
  public void setSpouseID(String setSpouseID) { this.spouseID = setSpouseID; }

}
