package com.MayDevelops.familymapclient;


import com.MayDevelops.familymapclient.Controllers.SearchController;
import com.MayDevelops.familymapclient.Models.FilterModel;
import com.MayDevelops.familymapclient.Models.IntermediateData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Filter;

import Models.Event;
import Models.Person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class IntermediateDataTest {


  @Before
  public void setUp() {
    Event eOne = new Event("1", "OptimusPrime", "1", "Russia", "Smell", "Mustache", 1, 2, 111);
    Event eTwo = new Event("1-2", "OptimusPrime", "1", "Ussiar", "Stink", "HandelBar", 12, 23, 222);
    Event eThree = new Event("1-2-3", "OptimusPrime", "1", "Ssiaru", "Stankin", "Curler", 123, 234, 333);
    Event eFour = new Event("1-2-3-4", "OptimusPrime", "1-2", "Siarus", "PooStink", "HairDryer", 1234, 2345, 444);
    Event eFive = new Event("1-2-3-4-5", "OptimusPrime", "1-2", "Iaruss", "VStank", "Laughed", 12345, 23456, 555);

    Person pOne = new Person("1", "OptimusPrime", "Bob", "Saggit", "F", "1-2-3", "1-2-3-4", null);
    Person pTwo = new Person("1-2", "OptimusPrime", "Bill", "Saggy", "F", null, null, null);
    Person pThree = new Person("1-2-3", "OptimusPrime", "Boop", "Saggin", "M", null, null, null);
    Person pFour = new Person("1-2-3-4", "OptimusPrime", "Bop", "Sagger", "M", "1-2-3-4-5", "TheMom", null);
    Person pFive = new Person("1-2-3-4-5", "OptimusPrime", "Pob", "Reggas", "M", null, null, null);
    Person pSix = new Person("OptimusPappi", "OptimusPrime", "Oppy", "Poppy", "f", null, null, null);
    Person mom = new Person("TheMom", "OptimusPrime", "Mom", "LastMom", "f", null, null, null);

    Person user = new Person("OppyPoppin", "Different", "First", "Last", "m", "1", "1-2", null);

    Event[] events = new Event[]{eOne, eTwo, eThree, eFour, eFive};
    Person[] persons = new Person[]{user, pOne, pTwo, pThree, pFour, pFive, pSix, mom};

    Map<String, Person> personMap = new HashMap<String, Person>();
    IntermediateData data = IntermediateData.getInstance();
    data.setUser(user);

    for (int i = 0; i < persons.length; i++) {
      String personID = persons[i].getPersonID();
      personMap.put(personID, persons[i]);
    }

    Map<String, Event> EventMap = new HashMap<String, Event>();

    for (int i = 0; i < events.length; i++) {
      String eventID = events[i].getEventID();
      EventMap.put(eventID, events[i]);
    }

    data.setEvents(EventMap);
    data.setPersons(personMap);
    data.InitializeData();

  }

  @Test
  public void GetPeopleTest() {
    IntermediateData data = IntermediateData.getInstance();
    Map<String, Person> test = data.getPersons();
    assertNotNull(test);
    assertEquals(8, test.size());

    assertEquals("Bob", test.get("1").getFirstName());
    assertEquals("Bill", test.get("1-2").getFirstName());
    assertEquals("Boop", test.get("1-2-3").getFirstName());
    assertEquals("Bop", test.get("1-2-3-4").getFirstName());
    assertEquals("Pob", test.get("1-2-3-4-5").getFirstName());
    assertEquals("Oppy", test.get("OptimusPappi").getFirstName());
    assertEquals("Mom", test.get("TheMom").getFirstName());

    assertEquals("Saggit", test.get("1").getLastName());
    assertEquals("Saggy", test.get("1-2").getLastName());
    assertEquals("Saggin", test.get("1-2-3").getLastName());
    assertEquals("Sagger", test.get("1-2-3-4").getLastName());
    assertEquals("Reggas", test.get("1-2-3-4-5").getLastName());
    assertEquals("Poppy", test.get("OptimusPappi").getLastName());
    assertEquals("LastMom", test.get("TheMom").getLastName());

    assertNull(test.get("1").getSpouseID());
    assertNull(test.get("1-2").getSpouseID());
    assertNull(test.get("1-2-3").getSpouseID());
    assertNull(test.get("1-2-3-4").getSpouseID());
    assertNull(test.get("1-2-3-4-5").getSpouseID());
    assertNull(test.get("OptimusPappi").getSpouseID());
    assertNull(test.get("TheMom").getSpouseID());

  }

  @Test
  public void GetEventTest() {
    IntermediateData data = IntermediateData.getInstance();
    Map<String, Event> test = data.getEvents();
    assertNotNull(test);
    assertEquals(5, test.size());

    assertEquals("Smell", test.get("1").getCity());
    assertEquals("Stink", test.get("1-2").getCity());
    assertEquals("Stankin", test.get("1-2-3").getCity());
    assertEquals("PooStink", test.get("1-2-3-4").getCity());
    assertEquals("VStank", test.get("1-2-3-4-5").getCity());

    assertEquals(1, test.get("1").getLatitude());
    assertEquals(12, test.get("1-2").getLatitude());
    assertEquals(123, test.get("1-2-3").getLatitude());
    assertEquals(1234, test.get("1-2-3-4").getLatitude());
    assertEquals(12345, test.get("1-2-3-4-5").getLatitude());

    assertEquals("1", test.get("1").getPersonID());
    assertEquals("1", test.get("1-2").getPersonID());
    assertEquals("1", test.get("1-2-3").getPersonID());
    assertEquals("1-2", test.get("1-2-3-4").getPersonID());
    assertEquals("1-2", test.get("1-2-3-4-5").getPersonID());

  }

  @Test
  public void GetPaternalFamilyTest() {
    IntermediateData data = IntermediateData.getInstance();
    Set<String> paternalAncestors = data.getPaternalAncestors();

    assertNotNull(paternalAncestors);
    assertEquals(5, paternalAncestors.size());
    assertTrue(paternalAncestors.contains("1"));
    assertTrue(paternalAncestors.contains("1-2-3"));
    assertTrue(paternalAncestors.contains("1-2-3-4"));
    assertTrue(paternalAncestors.contains("1-2-3-4-5"));
    assertTrue(paternalAncestors.contains("TheMom"));
  }

  @Test
  public void GetMaternalFamilyTest() {
    IntermediateData data = IntermediateData.getInstance();
    Set<String> maternalAncestors = data.getMaternalAncestors();

    assertNotNull(maternalAncestors);
    assertEquals(1, maternalAncestors.size());
    assertTrue(maternalAncestors.contains("1-2"));
    assertFalse(maternalAncestors.contains("TheMom"));
  }

  @Test
  public void SortTest() {
    IntermediateData data = IntermediateData.getInstance();
    List<Event> eventArrayList = data.getAllPersonEvents().get("1");
    assertNotNull(eventArrayList);
    assertEquals(3, eventArrayList.size());

    Event eOne = new Event("1", "OptimusPrime", "1", "Russia", "Smell", "Mustache", 1, 2, 111);
    Event eTwo = new Event("1-2", "OptimusPrime", "1", "Ussiar", "Stink", "HandelBar", 12, 23, 222);
    Event eThree = new Event("1-2-3", "OptimusPrime", "1", "Ssiaru", "Stankin", "Curler", 123, 234, 333);
    Event eFour = new Event("e1-2-3-4", "Megatron", "1-2", "Siarus", "PooStink", "HairDryer", 1234, 2345, 444);
    Event eFive = new Event("e1-2-3-4-5", "Megatron", "1-2", "Iaruss", "VStank", "Laughed", 12345, 23456, 555);

    eventArrayList = data.SortEventsByYear(eventArrayList);

    assertEquals(eOne, eventArrayList.get(0));
    assertEquals(eTwo, eventArrayList.get(1));
    assertEquals(eThree, eventArrayList.get(2));

    eventArrayList = data.getAllPersonEvents().get("1-2");
    assertNotNull(eventArrayList);
    assertEquals(2, eventArrayList.size());


  }

  @Test
  public void EventFilterTest() {
    IntermediateData data = IntermediateData.getInstance();
    FilterModel filter = data.getFilter();

    Map<String, Event> test = data.GetDisplayedEvents();
    assertNotNull(test);
    assertEquals(5, test.size());

    Event eOne = new Event("1", "OptimusPrime", "Can", "Russia", "Smell", "Mustache", 1, 2, 111);
    Event eTwo = new Event("1-2", "OptimusPrime", "You", "Ussiar", "Stink", "HandelBar", 12, 23, 222);
    Event eThree = new Event("1-2-3", "OptimusPrime", "Feel", "Ssiaru", "Stankin", "Curler", 123, 234, 333);
    Event eFour = new Event("1-2-3-4", "Megatron", "The", "Siarus", "PooStink", "HairDryer", 1234, 2345, 444);
    Event eFive = new Event("1-2-3-4-5", "OptimusPrime", "Love", "Iaruss", "VStank", "Laughed", 12345, 23456, 555);

    assertEquals("Russia", test.get("1").getCountry());

    assertEquals("Ussiar", test.get("1-2").getCountry());

    assertEquals("Ssiaru", test.get("1-2-3").getCountry());

    assertEquals("Siarus", test.get("1-2-3-4").getCountry());

    assertEquals("Iaruss", test.get("1-2-3-4-5").getCountry());

    filter.getDisplayedEvents().remove("death");

    test = data.GetDisplayedEvents();
    assertNotNull(test);
    assertEquals(5, test.size());

    assertEquals("Stink", test.get("1-2").getCity());
    assertEquals("Curler", test.get("1-2-3").getEventType());
    assertEquals("1-2", test.get("1-2-3-4-5").getPersonID());
    filter.getDisplayedEvents().add("death");

  }

  @Test
  public void FamilyFilterTest() {
    IntermediateData data = IntermediateData.getInstance();
    FilterModel filter = data.getFilter();

    Map<String, Event> test = data.GetDisplayedEvents();
    assertNotNull(test);
    assertEquals(5, test.size());

    assertEquals("Russia", test.get("1").getCountry());
    assertEquals("Ussiar", test.get("1-2").getCountry());
    assertEquals("Ssiaru", test.get("1-2-3").getCountry());
    assertEquals("Siarus", test.get("1-2-3-4").getCountry());
    assertEquals("Iaruss", test.get("1-2-3-4-5").getCountry());

    filter.getDisplayedEvents().remove("death");

    test = data.GetDisplayedEvents();
    assertNotNull(test);
    assertEquals(5, test.size());

    assertEquals("Stink", test.get("1-2").getCity());
    assertEquals("Curler", test.get("1-2-3").getEventType());
    assertEquals("1-2", test.get("1-2-3-4-5").getPersonID());

    filter.setFemales(false);

    test = data.GetDisplayedEvents();
    assertNotNull(test);
    assertEquals(3, test.size());
    filter.setFemales(true);
    test = data.GetDisplayedEvents();
    assertNotNull(test);
    assertEquals(5, test.size());
    filter.setMales(false);
    test = data.GetDisplayedEvents();
    assertNotNull(test);
    assertEquals(2, test.size());
    filter.setMales(true);
    test = data.GetDisplayedEvents();
    assertNotNull(test);
    assertEquals(5, test.size());
    filter.setFathersSide(false);
    test = data.GetDisplayedEvents();
    assertNotNull(test);
    assertEquals(1, test.size());
    filter.setFathersSide(true);
    test = data.GetDisplayedEvents();
    assertNotNull(test);
    assertEquals(5, test.size());
    filter.setMothersSide(false);
    test = data.GetDisplayedEvents();
    assertNotNull(test);
    assertEquals(4, test.size());
    filter.setMothersSide(true);
    test = data.GetDisplayedEvents();
    assertNotNull(test);
    assertEquals(5, test.size());
  }

  @Test
  public void SearchTest() {
    IntermediateData data = IntermediateData.getInstance();
    Map<String, Person> dataPersons = data.getPersons();
    Map<String, Event> dataEvents = data.getEvents();
    List<Object> objectList = new ArrayList<>();


    assertEquals(8, dataPersons.size());
    assertEquals(5, dataEvents.size());

    String searchInput = "a";
    for (Person p : dataPersons.values()) {
      if (p.getFirstName().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(p);
      } else if (p.getLastName().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(p);
      }
    }
    assertEquals(7, objectList.size());
    objectList.clear();

    searchInput = "buffalo";
    for (Person p : dataPersons.values()) {
      if (p.getFirstName().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(p);
      } else if (p.getLastName().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(p);
      }
    }
    assertEquals(0, objectList.size());
    objectList.clear();

    searchInput = "Bob";
    for (Person p : dataPersons.values()) {
      if (p.getFirstName().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(p);
      } else if (p.getLastName().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(p);
      }
    }
    assertEquals(1, objectList.size());
    objectList.clear();


    searchInput = "M";
    for (Event e : dataEvents.values()) {
      if (e.getEventType().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(e);
      } else if (e.getCountry().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(e);
      } else if (e.getCity().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(e);
      }
    }
    assertEquals(1, objectList.size());
    objectList.clear();

    searchInput = "Zebra";
    for (Event e : dataEvents.values()) {
      if (e.getEventType().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(e);
      } else if (e.getCountry().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(e);
      } else if (e.getCity().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(e);
      }
    }
    assertEquals(0, objectList.size());
    objectList.clear();

    searchInput = "e";
    for (Event e : dataEvents.values()) {
      if (e.getEventType().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(e);
      } else if (e.getCountry().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(e);
      } else if (e.getCity().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(e);
      }
    }
    assertEquals(5, objectList.size());
    objectList.clear();

  }


}