/**
 * A parent class for all of the results. This class holds the different error messages that can be
 * used by the children classes in the results directory.
 */
public class Result {

  String invalidAuthToken;
  String invalidEventID;
  String invalidPassword;
  String invalidPersonID;
  String invalidUsername;
  String invalidEmail;
  String invalidValue;
  String invalidRequestProperty;
  String usernameAlreadyTaken;
  String internalServerError;

  /**
   * Initializes the error messages that will be used in the server
   */
  public Result() {
    invalidAuthToken = "Error: The authorization token provided is invalid.\n";
    invalidEventID = "Error: Invalid eventID parameter, Requested event does not belong to this user.\n";
    invalidPersonID = "Error: The personID is invalid, requested person does not belong to this user or does not exist.\n";
    invalidPassword = "Error: The password is incorrect.\n";
    invalidUsername = "Error: The username is incorrect.\n";
    invalidEmail = "Error: The email is incorrect.\n";
    invalidValue = "Error: Request property missing or has invalid value.\n";
    invalidRequestProperty = "Error: Invalid request property.\n";
    usernameAlreadyTaken = "Error: Username is already taken.\n";
    internalServerError = "Error: Internal server error.\n";
  }
}

