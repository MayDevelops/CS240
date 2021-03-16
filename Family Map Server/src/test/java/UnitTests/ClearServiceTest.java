package UnitTests;

import DataAccessObjects.DataAccessException;
import Service.Results.ClearResult;
import Service.Services.ClearService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class ClearServiceTest {

  @Test
  public void clearDbTest() throws DataAccessException {
    ClearService clearService = new ClearService();
    ClearResult clearResult = clearService.ClearDatabase();

    Assert.assertTrue(clearResult.getResult().equals("Clear succeeded."));
  }
}
