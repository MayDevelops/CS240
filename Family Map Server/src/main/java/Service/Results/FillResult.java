package Service.Results;

/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class FillResult {

  private String message;

  public FillResult(String setMessage) { this.message = setMessage; }

  public String getMessage() { return message; }
  public void SetMessage(String setMessage) { this.message = setMessage; }
}
