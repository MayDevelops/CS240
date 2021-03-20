package DataAccessObjects;
import Models.*;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class used for accessing the User table in the database.
 */
public class UsersDAO {

  private final Connection conn;

  public UsersDAO(Connection conn) {this.conn = conn; }
  public UsersDAO() { this.conn = null; }

  /**
   * An insert method that will insert a User object into the User table.
   *
   * @param user the incoming user object to be inserted into the table.
   */
  public void Insert(User user) throws DataAccessException {
    String sql = "INSERT INTO Users (Username, Password, Email, First_Name, Last_Name, Gender, Person_ID)" +
            " VALUES(?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, user.getUsername());
      stmt.setString(2, user.getPassword());
      stmt.setString(3, user.getEmail());
      stmt.setString(4, user.getFirstName());
      stmt.setString(5, user.getLastName());
      stmt.setString(6, user.getGender());
      stmt.setString(7, user.getPersonID());

      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error encountered while inserting user into the database");
    }
  }

  /**
   * Deletes a user from the User table in the database.
   *
   * @param username the user to be deleted.
   */
  public void Delete(String username) throws DataAccessException{
    String sql = "DELETE FROM Users WHERE Username = ?;";
    try(PreparedStatement stmt = conn.prepareStatement(sql)) {
    stmt.setString(1, username);
    stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while deleting into the database");
    }
  }

  /**
   * Clears the entire User table.
   *
   * @return true or false depending on if the table is cleared correctly.
   */
  public boolean Clear() throws DataAccessException {
    boolean success = false;
    PreparedStatement stmt = null;
    String sqlUsersDrop = "DROP TABLE IF EXISTS Users;";
    String sqlUsersCreate = "CREATE TABLE IF NOT EXISTS `Users` (\n" +
            "\t`Username`\ttext NOT NULL,\n" +
            "\t`Password`\ttext NOT NULL,\n" +
            "\t`Email`\ttext NOT NULL,\n" +
            "\t`First_Name`\ttext NOT NULL,\n" +
            "\t`Last_Name`\ttext NOT NULL,\n" +
            "\t`Gender`\ttext NOT NULL,\n" +
            "\t`Person_ID`\ttext NOT NULL,\n" +
            "\tPRIMARY KEY(`Username`)\n" +
            ");\n";
    try {
      stmt = conn.prepareStatement(sqlUsersDrop);
      stmt.executeUpdate();
      stmt = conn.prepareStatement(sqlUsersCreate);
      stmt.executeUpdate();
      success = true;
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Error Clearing User's Table\n");
    }
    return success;
  }

  public User find(String username) throws DataAccessException {
    User user;
    ResultSet rs = null;
    String sql = "SELECT * FROM Users WHERE Username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      rs = stmt.executeQuery();
      if (rs.next()) {
        user = new User(rs.getString("Username"), rs.getString("Password"),
                rs.getString("Email"), rs.getString("First_Name"), rs.getString("Last_Name"),
                rs.getString("Gender"), rs.getString("Person_ID"));
        return user;
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

  public String GetUsername(User user) { return user.getUsername(); }
  public String GetUserPassword(User user) { return user.getPassword(); }

  public void SetUserName(String setUsername, User user) { user.setUsername(setUsername); }
  public void SetPassword(String setPassword, User user) { user.setPassword(setPassword); }
  public void SetEmail(String setEmail, User user) { user.setEmail(setEmail); }
  public void SetFirstName(String setFirstName, User user) { user.setFirstName(setFirstName); }
  public void SetLastName(String setLastName, User user) { user.setLastName(setLastName);}
  public void SetGender(String setGender, User user) { user.setGender(setGender); }
}
