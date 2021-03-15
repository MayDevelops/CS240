package Service.Results;

/**
 * A parent class for all of the results. This class holds the different error messages that can be
 * used by the children classes in the results directory.
 */
public class Result {

  public String invalidAuthToken;
  public String invalidDatabaseClear;
  public String invalidEventID;
  public String invalidPassword;
  public String invalidPersonID;
  public String invalidUsername;
  public String invalidEmail;
  public String invalidValue;
  public String invalidRequestProperty;
  public String usernameAlreadyTaken;
  public String internalServerError;
  public String invalidUsernameOrGenerations;
  public String invalidRequestData;

  /**
   * Initializes the error messages that will be used in the server
   */
  public Result() {
    invalidAuthToken = "Error: The authorization token provided is invalid.\n";
    invalidDatabaseClear = "Error: The database was not cleared successfully.\n";
    invalidEventID = "Error: Invalid eventID parameter, Requested event does not belong to this user.\n";
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
}

