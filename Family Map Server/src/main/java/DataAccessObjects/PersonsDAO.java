package DataAccessObjects;
import Models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * A class used for accessing the Person table in the database.
 */
public class PersonsDAO {
  private final Connection conn;

  public PersonsDAO(Connection conn) {this.conn = conn; }
  /**
   * Inserts the person into the Person table in the database.
   * @param person the person to be inserted into the database.
   */
  public void Insert(Person person) throws DataAccessException {
    String sql = "INSERT INTO Persons (Person_ID, Username, First_Name, Last_Name, Gender, Father_ID, Mother_ID, Spouse_ID)" +
            " VALUES(?,?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, person.getPersonID());
      stmt.setString(2, person.getAssociatedUsername());
      stmt.setString(3, person.getFirstName());
      stmt.setString(4, person.getLastName());
      stmt.setString(5, person.getGender());
      stmt.setString(6, person.getFatherID());
      stmt.setString(7, person.getMotherID());
      stmt.setString(8, person.getSpouseID());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  /**
   * Deletes a person from the Person table in the database.
   * @param person the person to be deleted.
   * @return true or false depending on if the User object is correctly removed from the table.
   */
  public Boolean Delete(Person person) { return null; }

  /**
   * Clears the Person table in the database.
   * @return true or false depending on if the table is cleared correctly.
   */
  public boolean Clear() throws DataAccessException {
    boolean success = false;
    String sql = "DROP TABLE IF EXISTS Persons;";
    try(PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.executeUpdate();
      success = true;
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Error Clearing User's Table\n");
    }
    return success;
  }


  public Person find(String person_ID) throws DataAccessException {
    Person person;
    ResultSet rs = null;
    String sql = "SELECT * FROM Persons WHERE Person_ID = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, person_ID);
      rs = stmt.executeQuery();
      if (rs.next()) {
        person = new Person(rs.getString("Person_ID"), rs.getString("Username"),
                rs.getString("First_Name"), rs.getString("Last_Name"), rs.getString("Gender"),
                rs.getString("Father_ID"), rs.getString("Mother_ID"), rs.getString("Spouse_ID"));
        return person;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding event");
    } finally {
      if(rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

    }
    return null;
  }


  public String GetPersonID(Person person) { return person.getPersonID(); }
  public String GetFirstName(Person person) { return person.getFirstName(); }
  public String GetLastName(Person person) { return person.getLastName(); }
  public String GetGender(Person person) { return person.getGender(); }
  public String GetFatherID(Person person) { return person.getFatherID(); }
  public String GetMotherID(Person person) { return person.getMotherID(); }
  public String GetSpouseID(Person person) { return person.getSpouseID(); }
  public Set<String> GetAllPersons(String associatedUsername) { Set<String> toReturn = new TreeSet<>(); return toReturn; }

  public void setPersonID(String setPersonID, Person person) { person.setPersonID(setPersonID); }
  public void setFirstName(String setFirstName, Person person) { person.setFirstName(setFirstName); }
  public void setLastName(String setLastName, Person person) { person.setLastName(setLastName); }
  public void setGender(String setGender, Person person) { person.setGender(setGender); }
  public void setFatherID(String setFatherID, Person person) { person.setFatherID(setFatherID); }
  public void setMotherID(String setMotherID, Person person) { person.setMotherID(setMotherID);}
  public void setSpouseID(String setSpouseID, Person person) { person.setSpouseID(setSpouseID); }
  public void setAssociatedUsername(String setAssociatedUsername, Person person) { person.setAssociatedUsername(setAssociatedUsername); }
}
