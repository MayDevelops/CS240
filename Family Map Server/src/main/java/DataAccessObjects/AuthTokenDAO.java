package DataAccessObjects;

import Models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class used for accessing the AuthToken table in the database.
 */
public class AuthTokenDAO {

  private String userName;
  private String password;
  private String authToken;
  private Connection conn;

  public AuthTokenDAO() {
    userName = null;
    password = null;
    authToken = null;
  }

  public AuthTokenDAO(Connection conn) {this.conn = conn; }


  /**
   * Inserts the authToken into the AuthToken table in the database.
   * @param authToken the token to be inserted into the database.
   * @return true or false depending on if the token is correctly inserted into the table.
   */
  public void Insert(AuthToken authToken) throws DataAccessException {
    String sql = "INSERT INTO AuthToken (Username, Auth_Token) VALUES (?,?)";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, authToken.getUsername());
      stmt.setString(2, authToken.getAuthToken());

      stmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println(authToken.getUsername());
      System.out.println(authToken.getAuthToken());
      throw new DataAccessException("Error encountered while inserting into the database");
    }
  }

  public AuthToken find(String searchToken) throws DataAccessException {
    AuthToken token;
    ResultSet rs = null;
    String sql = "SELECT * FROM AuthToken WHERE Auth_Token = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, searchToken);
      rs = stmt.executeQuery();
      if (rs.next()) {
        token = new AuthToken(rs.getString("Username"), rs.getString("Auth_Token"));
        return token;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding token");
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

  /**
   * Deletes an authToken from the AuthToken table in the database.
   * @param authToken the token to be deleted.
   * @return true or false depending on if the authToken object is correctly removed from the table.
   */
  public Boolean Delete(AuthToken authToken) { return null; }

  /**
   * Clears the AuthToken table in the database.
   * @return true or false depending on if the table is cleared correctly.
   */
  public Boolean Clear(AuthToken authToken) { return null; }

  public String getAuthToken(User user) { return null; }
  public String getUserName(User user) { return user.getUsername(); }
  public String getPassword(User user) { return user.getPassword(); }

  public void setAuthToken(User user) { user.setAuthTokens(authToken); }




}
