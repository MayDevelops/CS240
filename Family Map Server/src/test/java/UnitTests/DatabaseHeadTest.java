package UnitTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.DatabaseHead;
import DataAccessObjects.UsersDAO;
import Models.Event;
import Models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseHeadTest {

  DatabaseHead db;
  Connection conn;

  @BeforeEach
  void setUp() throws DataAccessException {
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
  void openConnectionPass() {
    try {
      conn = db.getConnection();
      assertNotNull(conn);
    } catch (DataAccessException e) {

    }

  }

  @Test
  void openConnectionFail() {
    assertNull(db.conn);
  }

  @Test
  void getConnection() {
    try {
      assertNotNull(db.getConnection());
    } catch (DataAccessException e) {

    }

  }

  @Test
  void clearTablesPass() {
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