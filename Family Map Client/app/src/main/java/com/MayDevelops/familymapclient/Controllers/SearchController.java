package com.MayDevelops.familymapclient.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MayDevelops.familymapclient.Models.IntermediateData;
import com.MayDevelops.familymapclient.R;
import com.MayDevelops.familymapclient.RecyclerViews.SearchAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Models.Event;
import Models.Person;

public class SearchController extends AppCompatActivity {

  private final IntermediateData data = IntermediateData.getInstance();

  private EditText searchBar;
  private String searchInput;

  private RecyclerView searchRecycler;
  private RecyclerView.Adapter searchAdapter;


  /**
   * when nothing is typed it should be empty list
   * if whatever is typed returns an emtpy list, it should display nothing.
   * when they type garbage: it should return nothing when you are done typing, but along the way there would be stuff there.
   */


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_search);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    searchBar = findViewById(R.id.search_text);
    searchBar.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchInput = s.toString();
        UpdateUI();
      }

      @Override
      public void afterTextChanged(Editable s) {
      }
    });

    searchRecycler = findViewById(R.id.list_search_recycler);
    searchRecycler.setLayoutManager(new LinearLayoutManager(this));

  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      Intent intent = new Intent(this, MainController.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(intent);
    }

    return true;
  }

  private void UpdateUI() {
    List<Object> objectList = new ArrayList<>();

    Map<String, Person> dataPersons = data.getPersons();
    PopulatePersonsList(dataPersons, objectList);

    Map<String, Event> dataEvents = data.getEvents();
    PopulateEventsList(dataEvents, objectList);

    if (searchInput.equals("")) {
      objectList.clear();
      searchAdapter = new SearchAdapter(objectList, this);
      searchRecycler.setAdapter(searchAdapter);
    }
    if (objectList.size() != 0) {
      searchAdapter = new SearchAdapter(objectList, this);
      searchRecycler.setAdapter(searchAdapter);
    } else {
      objectList.clear();
      searchAdapter = new SearchAdapter(objectList, this);
      searchRecycler.setAdapter(searchAdapter);
    }
  }

  private void PopulatePersonsList(Map<String, Person> allPeople, List<Object> objectList) {
    for (Person p : allPeople.values()) {
      if (p.getFirstName().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(p);
      } else if (p.getLastName().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(p);
      }
    }
  }

  private void PopulateEventsList(Map<String, Event> allEvents, List<Object> objectList) {
    for (Event e : allEvents.values()) {
      if (e.getEventType().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(e);
      } else if (e.getCountry().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(e);
      } else if (e.getCity().toLowerCase().contains(searchInput.toLowerCase())) {
        objectList.add(e);
      }
    }
  }
}
