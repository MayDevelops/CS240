package com.MayDevelops.familymapclient.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.MayDevelops.familymapclient.Models.IntermediateData;
import com.MayDevelops.familymapclient.Models.PersonModel;
import com.MayDevelops.familymapclient.R;

import java.util.ArrayList;
import java.util.List;

import Models.Event;
import Models.Person;

public class PersonController extends AppCompatActivity {

  private final IntermediateData data = IntermediateData.getInstance();

  private Person person;

  private TextView firstName;
  private TextView lastName;
  private TextView gender;

  private ExpandableListView listView;
  private ExpandableListAdapter listAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_person);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle("FamilyMap");
    person = data.getSelectedPerson();

    firstName = findViewById(R.id.person_first_name);
    lastName = findViewById(R.id.person_last_name);
    gender = findViewById(R.id.person_gender);

    firstName.setText(person.getFirstName());
    lastName.setText(person.getLastName());
    if (person.getGender().toLowerCase().equals("m")) {
      gender.setText("Male");
    } else {
      gender.setText("Female");

    }

    listView = findViewById(R.id.expandable_list_person_activity);
    listView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
      if (groupPosition == 0) {
        Intent intent = new Intent(PersonController.this, EventController.class);
        intent.putExtra("Event", "Event");
        data.setSelectedEvent((Event) listAdapter.getChild(groupPosition, childPosition));
        startActivity(intent);
      } else {
        Intent intent = new Intent(PersonController.this, PersonController.class);
        data.setSelectedPerson((Person) listAdapter.getChild(groupPosition, childPosition));
        startActivity(intent);
      }
      return false;
    });
    Update();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        Intent intent = new Intent(this, MainController.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void Update() {
    List<Person> family = new ArrayList<>(data.FindFamily(person.getPersonID()));

    List<Event> eventsArrayList = new ArrayList<>(data.getAllPersonEvents().get(person.getPersonID()));
    eventsArrayList = data.SortEventsByYear(eventsArrayList);

    List<String> headers = new ArrayList<>();
    headers.add("Life Events");
    headers.add("Family");

    eventsArrayList = FilterEvents(eventsArrayList);
    family = FilterPersons(family);

    listAdapter = new PersonModel(this, headers, eventsArrayList, family, person);
    listView.setAdapter(listAdapter);
  }

  private List<Event> FilterEvents(List<Event> eventsList) {
    List<Event> temp = new ArrayList<>();
    for (Event e : eventsList) {
      if (data.GetDisplayedEvents().containsValue(e)) {
        temp.add(e);
      }
    }
    return temp;
  }

  private List<Person> FilterPersons(List<Person> personsList) {
    List<Person> filteredList = new ArrayList<>();

    for (Person p : personsList) {
      if (data.IsPersonDisplayed(p)) {
        filteredList.add(p);
      }
    }
    return filteredList;
  }

}
