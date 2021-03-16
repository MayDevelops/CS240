package Service.Results;

/**
 * A parent class for all of the results. This class holds the different error messages that can be
 * used by the children classes in the results directory.
 */
public class Result {

  private String invalidAuthToken;
  private String invalidDatabaseClear;
  private String invalidEventID;
  private String invalidInput;
  private String invalidPassword;
  private String invalidPersonID;
  private String invalidUsername;
  private String invalidEmail;
  private String invalidValue;
  private String invalidRequestProperty;
  private String usernameAlreadyTaken;
  private String internalServerError;
  private String invalidUsernameOrGenerations;
  private String invalidRequestData;





  /**
   * Initializes the error messages that will be used in the server
   */
  public Result() {
    invalidAuthToken = "Error: The authorization token provided is invalid.\n";
    invalidDatabaseClear = "Error: The database was not cleared successfully.\n";
    invalidEventID = "Error: Invalid eventID parameter, Requested event does not belong to this user.\n";
    invalidInput = "Error: Invalid input.\n";
    invalidPersonID = "Error: The personID is invalid, requested person does not belong to this user or does not exist.\n";
    invalidPassword = "Error: The password is incorrect.\n";
    invalidUsername = "Error: The username is incorrect.\n";
    invalidUsernameOrGenerations = "Error: Invalid username or generations parameter.\n";
    invalidRequestData = "Error: Invalid request data.\n";
    invalidEmail = "Error: The email is incorrect.\n";
    invalidValue = "Error: Request property missing or has invalid value.\n";
    invalidRequestProperty = "Error: Invalid request property.\n";
    usernameAlreadyTaken = "Error: Username is already taken by another user.\n";
    internalServerError = "Error: Internal server error.\n";
  }


  public String getInvalidAuthToken() { return invalidAuthToken; }
  public String getInvalidDatabaseClear() { return invalidDatabaseClear; }
  public String getInvalidEventID() { return invalidEventID; }
  public String getInvalidInput() { return invalidInput; }
  public String getInvalidPassword() { return invalidPassword; }
  public String getInvalidPersonID() { return invalidPersonID; }
  public String getInvalidUsername() { return invalidUsername; }
  public String getInvalidEmail() { return invalidEmail; }
  public String getInvalidValue() { return invalidValue; }
  public String getInvalidRequestProperty() { return invalidRequestProperty; }
  public String getUsernameAlreadyTaken() { return usernameAlreadyTaken; }
  public String getInternalServerError() { return internalServerError; }
  public String getInvalidUsernameOrGenerations() { return invalidUsernameOrGenerations; }
  public String getInvalidRequestData() { return invalidRequestData; }
}

