/**
 * A class used for accessing the AuthToken table in the database.
 */
public class AuthTokenDAO {

  private String userName;
  private String password;
  private String authToken;

  /**
   * Inserts the authToken into the AuthToken table in the database.
   * @param authToken the token to be inserted into the database.
   * @return true or false depending on if the token is correctly inserted into the table.
   */
  public Boolean Insert(AuthToken authToken) { return null; }

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
