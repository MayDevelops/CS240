package UnitTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.DatabaseHead;
import DataAccessObjects.PersonsDAO;

import Models.Person;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonsDAOTest {
  private DatabaseHead db;
  private Person bestPerson;
  private PersonsDAO pDao;

  @BeforeEach
  public void setUp() throws DataAccessException
  {
    db = new DatabaseHead();
    bestPerson = new Person("Optimus123", "OptimusPrime123", "Optimus", "Prime",
            "M", "OptimusPapa001", "OptimusMama001", "000000");

    Connection conn = db.getConnection();
    db.ClearTables();
    pDao = new PersonsDAO(conn);
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    db.closeConnection(false);
  }
  @Test
  void InsertPass() throws DataAccessException {
    pDao.Insert(bestPerson);

    Person compareTest = pDao.find(bestPerson.getPersonID());

    assertNotNull(compareTest);

    assertEquals(bestPerson, compareTest);
  }

  @Test
  void InsertFail() throws DataAccessException {
    pDao.Insert(bestPerson);
    assertThrows(DataAccessException.class, ()-> pDao.Insert(bestPerson));
  }


  @Test
  void FindPass() {
    try {
      assertNull(pDao.find("Optimus123"));
      pDao.Insert(bestPerson);
      assertNotNull(pDao.find("Optimus123"));

      Person testPerson = pDao.find("Optimus123");

      assertEquals(bestPerson, testPerson);
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding Person\n");

    }
  }

  @Test
  void FindFail() {
    try {
      assertNull(pDao.find("Optimus123"));
      pDao.Insert(bestPerson);
      assertNotNull(pDao.find("Optimus123"));
      assertNull(pDao.find("Doesn'tExist"));
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding Person\n");
    }
  }

  @Test
  void ClearPass() {
    try {
      assertNull(pDao.find("Optimus123"));
      pDao.Insert(bestPerson);
      assertNotNull(pDao.find("Optimus123"));
      assertTrue(pDao.Clear());
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding Person\n");

    }
  }
}