package com.MayDevelops.familymapclient.Models;

import android.provider.Settings;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Filter;

import Models.*;

public class IntermediateData {

  public static IntermediateData instance;

  private Map<String, Person> persons;
  private Map<String, Event> events;
  private Person user;



  public static IntermediateData getInstance() {
    synchronized (IntermediateData.class) {
      if(instance == null) {
        instance = new IntermediateData();
      }
    }

    return instance;
  }

  public IntermediateData(){}



  public void setPersons(Map<String, Person> persons) { this.persons = persons; }
  public void setEvents(Map<String, Event> events) { this.events = events; }
  public void setUser(Person user) { this.user = user; }

  public Person getUser() { return user; }


  public void ClearData() {
    Map<String, Person> clearPersons = new HashMap<>();
    Map<String, Event> clearEvents = new HashMap<>();
    Person clearPerson = new Person("0","0",
            "0","0","0","0","0","0");
    this.setPersons(clearPersons);
    this.setEvents(clearEvents);
    this.setUser(clearPerson);
  }
  public void PrintContents() {
    Log.d("User",user.getAssociatedUsername());
    Log.d("Persons Size", String.valueOf(persons.size()));
    Log.d("Events size", String.valueOf(events.size()));

  }

}