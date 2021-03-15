package Service.Requests;
import Models.*;

/**
 * A class that packages a gson request and turns it into a Java object.
 */
public class PersonRequest {

  private Person[] persons;
  private Boolean success;
  private String personID;
  private String associatedUsername;
  private String firstName;
  private String lastName;
  private String gender;
  private String fatherID;
  private String motherID;
  private String spouseID;

}
