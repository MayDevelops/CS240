package com.MayDevelops.familymapclient.Models;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Models.*;

public class IntermediateData {

  public static IntermediateData instance;


  private Map<String, List<Event>> allPersonEvents;
  private Map<String, List<Event>> paternalAncestorEvents;
  private Map<String, List<Event>> maternalAncestorEvents;
  private Map<String, Event> displayedEvents;
  private Map<String, MapColors> eventColor;
  private Map<String, Person> children;
  private Map<String, Person> persons;
  private Map<String, Event> events;

  private List<Person> paternalTree;
  private List<Person> maternalTree;

  private Set<String> paternalAncestors;
  private Set<String> maternalAncestors;

  private static List<String> eventTypes;

  private SettingsModel settingsModel;
  private FilterModel filter;
  private Person user;
  private Person selectedPerson;
  private Event selectedEvent;

  public static IntermediateData getInstance() {
    synchronized (IntermediateData.class) {
      if (instance == null) {
        instance = new IntermediateData();
      }
    }
    return instance;
  }

  public IntermediateData() {
    if (settingsModel == null) {
      settingsModel = new SettingsModel();
    }
  }

  public boolean IsPersonDisplayed(Person person) {
    if (! filter.isMales() && person.getGender().toLowerCase().equals("m")) {
      return false;
    } else if (! filter.isFemales() && person.getGender().toLowerCase().equals("f")) {
      return false;
    } else if (! filter.isFathersSide() && paternalAncestors.contains(person.getPersonID())) {
      return false;
    } else {
      return filter.isMothersSide() || ! maternalAncestors.contains(person.getPersonID());
    }
  }

  public List<Event> SortEventsByYear(List<Event> events) {
    List<Event> sortedEvents = new ArrayList<>();
    List<Event> currentEvents = new ArrayList<>(events);

    while (currentEvents.size() > 0) {
      int index = 0;
      Event event = currentEvents.get(0);
      for (int i = 0; i < currentEvents.size(); i++) {
        if (currentEvents.get(i).getYear() < event.getYear()) {
          event = currentEvents.get(i);
          index = i;
        }
      }
      sortedEvents.add(event);
      currentEvents.remove(index);
    }
    return sortedEvents;
  }

  public Map<String, Event> GetDisplayedEvents() {
    displayedEvents = new HashMap<>();

    for (Event e : events.values()) {
      Person p = getPersons().get(e.getEventID());
      if (! IsPersonDisplayed(p)) {
        //do nothing
      } else if (! filter.ContainsEventType(e.getEventType())) {
        //do nothing
      } else {
        displayedEvents.put(e.getEventID(), e);
      }
    }
    return displayedEvents;
  }

  public List<Person> FindFamily(String personID) {
    Person person = getPersons().get(personID);
    List<Person> personList = new ArrayList<>();

    if (getPersons().get(person.getSpouseID()) != null) {
      personList.add(getPersons().get(person.getSpouseID()));
    }
    if (getPersons().get(person.getMotherID()) != null) {
      personList.add(getPersons().get(person.getMotherID()));
    }
    if (getPersons().get(person.getFatherID()) != null) {
      personList.add(getPersons().get(person.getFatherID()));
    }
    if (getChildren().get(person.getPersonID()) != null) {
      personList.add(getChildren().get(person.getPersonID()));
    }

    return personList;
  }

  public void InitializeData() {
    ArrayList<Event> eventsArray = new ArrayList<>(events.values());

    eventColor = new HashMap<>();
    eventTypes = new ArrayList<>();
    for (int i = 0; i < eventsArray.size(); i++) {
      if (! eventColor.containsKey(eventsArray.get(i).getEventType().toLowerCase())) {
        eventColor.put(eventsArray.get(i).getEventType().toLowerCase(),
                new MapColors(eventsArray.get(i).getEventType().toLowerCase()));

        eventTypes.add(eventsArray.get(i).getEventType().toLowerCase());
      }
    }
    instance.setEventTypes(eventTypes);


    paternalAncestors = new HashSet<>();
    AncestorHelper(user.getFatherID(), paternalAncestors);
    maternalAncestors = new HashSet<>();
    AncestorHelper(user.getMotherID(), maternalAncestors);


    allPersonEvents = new HashMap<>();
    for (Person p : persons.values()) {
      ArrayList<Event> eventList = new ArrayList<Event>();

      for (Event e : events.values()) {
        if (p.getPersonID().equals(e.getPersonID())) {
          eventList.add(e);
        }
      }
      allPersonEvents.put(p.getPersonID(), eventList);
    }

    paternalAncestorEvents = new HashMap<>();
    for (String s : paternalAncestors) {
      ArrayList<Event> eventList = new ArrayList<>();

      for (Event e : events.values()) {
        if (e.getPersonID().equals(s)) {
          eventList.add(e);
        }
      }
      paternalAncestorEvents.put(s, eventList);
    }

    maternalAncestorEvents = new HashMap<>();
    for (String s : maternalAncestors) {
      ArrayList<Event> eventList = new ArrayList<>();

      for (Event e : events.values()) {
        if (e.getPersonID().equals(s)) {
          eventList.add(e);
        }
      }
      maternalAncestorEvents.put(s, eventList);
    }

    //Log.d("P. Ancestor Size ", String.valueOf(paternalAncestorEvents.size()));
    //Log.d("M. Ancestor Size: ", String.valueOf(maternalAncestorEvents.size()));

    children = new HashMap<>();
    for (Person e : persons.values()) {

      if (e.getFatherID() != null) {
        children.put(e.getFatherID(), e);
      }
      if (e.getMotherID() != null) {
        children.put(e.getMotherID(), e);
      }
    }

    if (settingsModel == null) {
      settingsModel = new SettingsModel();
    }
    if (filter == null) {
      filter = new FilterModel();
    }

    paternalTree = new ArrayList<>();
    maternalTree = new ArrayList<>();
    if (user.getGender().toLowerCase().equals("m") ? paternalTree.add(user) : maternalTree.add(user))
      ;
    if (user.getSpouseID() != null && persons.get(user.getSpouseID()).getGender().toLowerCase().equals("m")) {
      paternalTree.add(persons.get(user.getSpouseID()));
    } else if (user.getSpouseID() != null && persons.get(user.getSpouseID()).getGender().toLowerCase().equals("f")) {
      maternalTree.add(persons.get(user.getSpouseID()));
    }


    BuildGenderTree(user);

    //Log.d("Paternal Size: ", String.valueOf(paternalTree.size()));
    //Log.d("Maternal Size: ", String.valueOf(maternalTree.size()));
  }

  private void AncestorHelper(String personID, Set<String> personSet) {
    if (personID == null) {
      return;
    }
    personSet.add(personID);
    Person person = persons.get(personID);

    if (person.getFatherID() != null) {
      AncestorHelper(person.getFatherID(), personSet);
    }

    if (person.getMotherID() != null) {
      AncestorHelper(person.getMotherID(), personSet);
    }
  }

  public void ClearData() {
    Map<String, Person> clearPersons = new HashMap<>();
    Map<String, Event> clearEvents = new HashMap<>();
    Person clearPerson = new Person("0", "0",
            "0", "0", "0", "0", "0", "0");
    this.setPersons(clearPersons);
    this.setEvents(clearEvents);
    this.setUser(clearPerson);
  }

  public void PrintContents() {
    Log.d("User", user.getAssociatedUsername());
    Log.d("Persons Size", String.valueOf(persons.size()));
    Log.d("Events size", String.valueOf(events.size()));

  }

  private void BuildGenderTree(Person person) {
    if (person.getFatherID() == null || person.getMotherID() == null) {
      return;
    }

    Person temp = persons.get(person.getFatherID());

    paternalTree.add(temp);
    BuildGenderTree(temp);
    maternalTree.add(persons.get(person.getMotherID()));
    BuildGenderTree(persons.get(person.getMotherID()));
  }


  public void setPersons(Map<String, Person> persons) { this.persons = persons; }
  public void setEvents(Map<String, Event> events) { this.events = events; }
  public void setUser(Person user) { this.user = user; }
  public void setSettingsModel(SettingsModel settingsModel) { this.settingsModel = settingsModel; }
  public void setEventTypes(List<String> eventTypes) { this.eventTypes = eventTypes; }
  public void setSelectedPerson(Person selectedPerson) { this.selectedPerson = selectedPerson; }
  public void setSelectedEvent(Event selectedEvent) { this.selectedEvent = selectedEvent; }

  public Map<String, Person> getPersons() { return persons; }
  public Map<String, Event> getEvents() { return events; }
  public Map<String, List<Event>> getAllPersonEvents() { return allPersonEvents; }
  public Map<String, MapColors> getEventColor() { return eventColor; }
  public Map<String, Person> getChildren() { return children; }
  public Set<String> getPaternalAncestors() { return paternalAncestors; }
  public Set<String> getMaternalAncestors() { return maternalAncestors; }
  public List<Person> getPaternalTree() { return paternalTree; }
  public List<Person> getMaternalTree() { return maternalTree; }
  public Person getUser() { return user; }
  public SettingsModel getSettingsModel() { return settingsModel; }
  public FilterModel getFilter() { return filter; }
  public Person getSelectedPerson() { return selectedPerson; }
  public Event getSelectedEvent() { return selectedEvent; }
  public static List<String> getEventTypes() { return eventTypes; }
}