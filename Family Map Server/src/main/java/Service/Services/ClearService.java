package Service.Services;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.DatabaseHead;
import Service.Results.*;

/**
 * Class that will handle Clear business logic and determine if a request was successful.
 */
public class ClearService {
  private ClearResult clearResult;

  /**
   * Function that clears the database.
   *
   * @return a result to determine if the database was cleared or not.
   */
  public ClearResult ClearDatabase() throws DataAccessException {
    DatabaseHead head = new DatabaseHead();
    try {
      System.out.println("Attempting to clear the database.");

      head.getConnection();
      head.ClearTables();
      head.closeConnection(true);

      System.out.println("Clear was successful!\n");

      clearResult = new ClearResult("Clear succeeded.");
      return clearResult;
    } catch (DataAccessException e) {
      System.out.println("Clear did not succeed.");
      head.closeConnection(false);
      clearResult = new ClearResult(clearResult.getInvalidDatabaseClear());
      return clearResult;
    }
  }
}
