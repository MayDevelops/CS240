package Service.Results;
/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class LoadResult {
  private String message;

  /**
   * Generates the response body for the service.
   * @param s determines if the result is success or failure.
   */
  public LoadResult(String s) {
   this.message = s;
  }

  public String getMessage() { return message; }

  public void setMessage(String message) { this.message = message; }
}
