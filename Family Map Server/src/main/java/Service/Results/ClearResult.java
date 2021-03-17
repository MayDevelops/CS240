package Service.Results;
/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class ClearResult extends  Result{

  private String message;

  public ClearResult(String setMessage) { this.message = setMessage; }

  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }

}
