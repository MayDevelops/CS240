package Service.Results;
/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class RegisterResult {

  private String authToken;
  private String username;
  private String personID;
  private String errorMessage;

  public RegisterResult() {}
  public RegisterResult(String setError) { this.setErrorMessage(setError); }

  /**
   * The constructor for the register result that will either create a success or failure message.
   * @param setAuthToken the authtoken to be assigned to the user if successful.
   * @param setUsername the username associated with the user.
   * @param setPersonID the unique personID associated with the user.
   */
  public RegisterResult(String setAuthToken, String setUsername, String setPersonID) {

      this.setAuthToken(setAuthToken);
      this.setUsername(setUsername);
      this.setPersonID(setPersonID);
      this.errorMessage = null;
  }

  public String getAuthToken() { return authToken; }
  public String getUsername() { return username; }
  public String getPersonID() { return personID; }
  public String getErrorMessage() { return errorMessage; }

  public void setAuthToken(String authToken) { this.authToken = authToken; }
  public void setUsername(String username) { this.username = username; }
  public void setPersonID(String personID) { this.personID = personID; }
  public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

}
