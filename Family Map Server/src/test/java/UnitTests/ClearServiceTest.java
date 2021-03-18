package UnitTests;

import DataAccessObjects.DataAccessException;
import Service.Results.ClearResult;
import Service.Services.ClearService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ClearServiceTest {

  @Test
  public void ClearPass() throws DataAccessException {
    ClearService clearService = new ClearService();
    ClearResult clearResult = clearService.ClearDatabase();

    assertEquals(clearResult.getMessage(), "Clear succeeded.");
  }
}
