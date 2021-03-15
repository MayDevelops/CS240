package Service.Results;
/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class ClearResult extends  Result{

  private String result;

  public ClearResult(String setResult) { this.result = setResult; }

  public String getResult() { return result; }
  public void setResult(String result) { this.result = result; }

}
