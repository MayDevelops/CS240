package Service.Results;

/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class LoginResult {

  private String authToken;
  private String username;
  private String personID;
  private String message;

  public LoginResult(String setMessage) { this.setMessage(setMessage); }

  public LoginResult(String setAuthToken, String setUsername, String setPersonID) {
    this.setAuthToken(setAuthToken);
    this.setUsername(setUsername);
    this.setPersonID(setPersonID);
  }

  public String getAuthToken() { return authToken; }
  public String getUsername() { return username; }
  public String getPersonID() { return personID; }
  public String getMessage() { return message; }

  public void setAuthToken(String setAuthToken) { this.authToken = setAuthToken; }
  public void setUsername(String setUsername) { this.username = setUsername; }
  public void setPersonID(String setPersonID) { this.personID = setPersonID; }
  public void setMessage(String setMessage) { this.message = setMessage; }
}
