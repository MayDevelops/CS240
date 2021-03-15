package Service.Results;
/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class FillResult extends  Result {

  /**
   * Generates the response body for the service.
   * @param success determines if the result is success or failure.
   */
  public FillResult(Boolean success) {
    if(success) {
      //success message
    } else {
      //error message
    }
  }
}
