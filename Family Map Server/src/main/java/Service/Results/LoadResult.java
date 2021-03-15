package Service.Results;
/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class LoadResult extends  Result {

  private Integer numUsers;
  private Integer numPersons;
  private Integer numEvents;


  /**
   * Generates the response body for the service.
   * @param success determines if the result is success or failure.
   */
  public LoadResult(Boolean success) {
    if(success) {
      //success message
    } else {
      //error message
    }
  }



}
