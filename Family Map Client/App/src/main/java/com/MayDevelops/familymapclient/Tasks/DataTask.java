package com.MayDevelops.familymapclient.Tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.MayDevelops.familymapclient.Models.IntermediateData;
import com.MayDevelops.familymapclient.ProxyServer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Models.Event;
import Models.Person;
import Results.EventResult;
import Results.PersonResult;

public class DataTask extends AsyncTask<String, Boolean, Boolean> {

  private String serverHost;
  private String ipAddress;
  private DataContext context;
  IntermediateData data = IntermediateData.getInstance();


  public DataTask(String setServer, String setIP, DataTask.DataContext setContext) {
    serverHost = setServer;
    ipAddress = setIP;
    context = setContext;
  }

  @Override
  protected Boolean doInBackground(String... authtoken) {
    ProxyServer server = ProxyServer.initialize();
    PersonResult personResult = server.Persons(serverHost,ipAddress, authtoken[0]);
    EventResult eventResult = server.Events(serverHost,ipAddress,authtoken[0]);

    return sendDataToModel(personResult,eventResult);
  }

  @Override
  protected void onPostExecute(Boolean loginOkay) {
    if(loginOkay) {
      Person user = data.getUser();
      String successMessage = "Welcome, " + user.getFirstName() + " " + user.getLastName();
      context.onExecuteCompleteData(successMessage);
    }
  }

  public interface DataContext {
    void onExecuteCompleteData(String message);
  }

  private boolean sendDataToModel(PersonResult personResult, EventResult eventResult) {
    return (InitializePersons(personResult) && InitializeEvents(eventResult));
  }

  private boolean InitializeEvents(EventResult eventResult) {
    if(eventResult.getMessage() == null) {
      Map<String, Event> eventMap = new HashMap<>();
      ArrayList<Event> eventArray = eventResult.getData();

      for(Event event : eventArray) {
        eventMap.put(event.getEventID(), event);
      }
      data.setEvents(eventMap);
      return true;
    }
    return false;
  }

  private boolean InitializePersons(PersonResult personResult) {
    if(personResult.getMessage() == null) {
      Map<String, Person> personMap = new HashMap<>();
      ArrayList<Person> personArray = personResult.getData();
      data.setUser(personArray.get(0));

      for(Person person : personArray) {
        personMap.put(person.getPersonID(), person);
      }

      data.setPersons(personMap);
      return true;
    }
    return false;
  }

}
