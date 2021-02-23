/**
 * A class used for accessing the User table in the database.
 */
public class UsersDAO {


  /**
   * An insert method that will insert a User object into the User table.
   *
   * @param user the incoming user object to be inserted into the table.
   * @return true or false depending on if the User object is correctly inserted into the table.
   */
  public Boolean insert(User user) {
    return null;
  }

  /**
   * Deletes a user from the User table in the database.
   *
   * @param user the user to be deleted.
   * @return true or false depending on if the User object is correctly deleted from the table.
   */
  public Boolean Delete(User user) {
    return null;
  }

  /**
   * Clears the entire User table.
   *
   * @return true or false depending on if the table is cleared correctly.
   */
  public Boolean Clear(User user) { return null; }

  public String GetUsername(User user) { return user.getUsername(); }
  public String GetUserPassword(User user) { return user.getPassword(); }

  public void SetUserName(String setUsername, User user) { user.setUsername(setUsername); }
  public void SetPassword(String setPassword, User user) { user.setPassword(setPassword); }
  public void SetEmail(String setEmail, User user) { user.setEmail(setEmail); }
  public void SetFirstName(String setFirstName, User user) { user.setFirstName(setFirstName); }
  public void SetLastName(String setLastName, User user) { user.setLastName(setLastName);}
  public void SetGender(String setGender, User user) { user.setGender(setGender); }
}
