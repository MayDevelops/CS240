package UnitTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.DatabaseHead;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseHeadTest {

  DatabaseHead db;
  Connection conn;

  @BeforeEach
  void setUp() {
    db = new DatabaseHead();
  }

  @AfterEach
  void tearDown() {
    try {
      if(db.conn != null) {
        db.closeConnection(false);
      }
    } catch (DataAccessException e) {

    }
  }

  @Test
  void OpenConnectionPass() {
    try {
      conn = db.getConnection();
      assertNotNull(conn);
    } catch (DataAccessException e) {

    }
  }

  @Test
  void OpenConnectionFail() {
    assertNull(db.conn);
  }

  @Test
  void GetConnectionPass() {
    try {
      assertNotNull(db.getConnection());
    } catch (DataAccessException e) {

    }
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