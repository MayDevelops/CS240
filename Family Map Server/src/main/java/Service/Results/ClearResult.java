package Service.Results;
/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class ClearResult extends  Result{

  public ClearResult(Boolean success) {
    if(success) {
      //success message
    } else {
      //error message
    }
  }
}
