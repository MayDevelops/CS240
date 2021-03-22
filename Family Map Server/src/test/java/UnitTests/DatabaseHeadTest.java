package UnitTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.DatabaseHead;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseHeadTest {

  DatabaseHead db = new DatabaseHead();
  Connection conn;

  @BeforeEach
  void setUp() throws DataAccessException {
    conn = db.getConnection();
  }

  @AfterEach
  void tearDown() throws DataAccessException {
    if (db.conn != null) {
      db.closeConnection(false);
    }
  }

  @Test
  void OpenConnectionPass() throws DataAccessException {
    assertNotNull(conn);
  }

  @Test
  void OpenConnectionFail() throws DataAccessException {
    db.closeConnection(false);
    assertNull(db.conn);
  }

  @Test
  void GetConnectionPass() throws DataAccessException {
    assertNotNull(db.getConnection());
  }

  @Test
  void ClearTablesPass() {
    boolean passed;

    try {
      db.getConnection();
      db.ClearTables();
      passed = true;
    } catch (DataAccessException e) {
      passed = false;
    }
    assertTrue(passed);
  }
}