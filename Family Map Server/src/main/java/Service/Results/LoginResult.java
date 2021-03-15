package Service.Results;
/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class LoginResult extends  Result {

  private String authToken;
  private String username;
  private String personID;

  /**
   * The constructor for the login result that will either create a success or failure message.
   * @param success determines if the result is success or failure.
   * @param setAuthToken the authtoken to be assigned to the user if successful.
   * @param setUsername the username associated with the user.
   * @param setPersonID the unique personID associated with the user.
   */
  public LoginResult(Boolean success, String setAuthToken, String setUsername, String setPersonID) {

    if(success) {
      this.setAuthToken(setAuthToken);
      this.setUsername(setUsername);
      this.setPersonID(setPersonID);
      //create success messages
    } else {
      //create error messages
    }
  }

  public String getAuthToken() { return authToken; }
  public String getUsername() { return username; }
  public String getPersonID() { return personID; }

  public void setAuthToken(String authToken) { this.authToken = authToken; }
  public void setUsername(String username) { this.username = username; }
  public void setPersonID(String personID) { this.personID = personID; }
}
