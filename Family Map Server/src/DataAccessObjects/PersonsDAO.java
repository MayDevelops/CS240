import java.util.Set;
import java.util.TreeSet;

/**
 * A class used for accessing the Person table in the database.
 */
public class PersonsDAO {

  /**
   * Inserts the person into the Person table in the database.
   * @param person the person to be inserted into the database.
   * @return true or false depending on if the User object is correctly inserted into the table.
   */
  public Boolean Insert(Person person) { return null; }

  /**
   * Deletes a person from the Person table in the database.
   * @param person the person to be deleted.
   * @return true or false depending on if the User object is correctly inserted into the table.
   */
  public Boolean Delete(Person person) { return null; }

  /**
   * Clears the Person table in the database.
   * @return true or false depending on if the User object is correctly inserted into the table.
   */
  public Boolean Clear() { return null; }

  public String GetPersonID(Person person) { return person.getPersonID(); }
  public String GetFirstName(Person person) { return person.getFirstName(); }
  public String GetLastName(Person person) { return person.getLastName(); }
  public String GetGender(Person person) { return person.getGender(); }
  public String GetFatherID(Person person) { return person.getFatherID(); }
  public String GetMotherID(Person person) { return person.getMotherID(); }
  public String GetSpouseID(Person person) { return person.getSpouseID(); }
  public Set <String> GetAllPersons(String associatedUsername) { Set<String> toReturn = new TreeSet<>(); return toReturn; }

  public void setPersonID(String setPersonID, Person person) { person.setPersonID(setPersonID); }
  public void setFirstName(String setFirstName, Person person) { person.setFirstName(setFirstName); }
  public void setLastName(String setLastName, Person person) { person.setLastName(setLastName); }
  public void setGender(String setGender, Person person) { person.setGender(setGender); }
  public void setFatherID(String setFatherID, Person person) { person.setFatherID(setFatherID); }
  public void setMotherID(String setMotherID, Person person) { person.setMotherID(setMotherID);}
  public void setSpouseID(String setSpouseID, Person person) { person.setSpouseID(setSpouseID); }
  public void setAssociatedUsername(String setAssociatedUsername, Person person) { person.setAssociatedUsername(setAssociatedUsername); }
}
