package com.MayDevelops.familymapclient.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.MayDevelops.familymapclient.Models.*;
import com.MayDevelops.familymapclient.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Models.Event;
import Models.Person;


public class MapController extends Fragment implements OnMapReadyCallback {

  IntermediateData data = IntermediateData.getInstance();

  private GoogleMap map;
  private SupportMapFragment supportMapFragment;
  private Map<Marker, Event> mapMarkers;

  private Map<String, Event> maleEventHolder = new HashMap<>();
  private Map<String, Event> femaleEventHolder = new HashMap<>();
  private Map<String, Event> paternalEventHolder = new HashMap<>();
  private Map<String, Event> maternalEventHolder = new HashMap<>();
  private Map<String, Event> mapEvents;

  private List<Polyline> polylineList;

  private Marker marker;

  private TextView name;
  private TextView event;
  private TextView year;

  private ImageView icon;

  private boolean isEvent = false;

  public MapController() {}

  public MapController(String eventID) {
    if (eventID != null) isEvent = true;
  }

  View.OnClickListener onClickListener = v -> UpdatePersonDisplay();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(! isEvent);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_map, container, false);

    supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
    assert supportMapFragment != null;
    supportMapFragment.getMapAsync(this);

    name = view.findViewById(R.id.person_name);
    event = view.findViewById(R.id.event_details);
    year = view.findViewById(R.id.year);
    icon = view.findViewById(R.id.map_icon);


    polylineList = new ArrayList<>();


    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d("Map Controller", "On Resume...");

    if (map != null && mapMarkers != null) {
      ClearMap();
      Event event = mapMarkers.get(marker);
      Log.d("Map Controller", "PutMarkers...");
      PutMarkers(map);

      if (marker == null) {
        if (! mapMarkers.containsValue(event)) {
          ClearLines();
        }
      }
      map.setMapType(data.getSettingsModel().getCurrMapType());
    }

    if (marker != null && mapMarkers != null) {
      DrawLines();
    }
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menus, menu);
    menu.findItem(R.id.menu_item_settings).setIcon(new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear).colorRes(R.color.white).sizeDp(30));
    menu.findItem(R.id.menu_item_search).setIcon(new IconDrawable(getActivity(), FontAwesomeIcons.fa_search).colorRes(R.color.white).sizeDp(25));
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.menu_item_search:
        UpdateSearch();
        return true;
      case R.id.menu_item_settings:
        UpdateSettings();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void onMapReady(GoogleMap googleMap) {
    PutMarkers(googleMap);
  }

  private void UpdatePersonDisplay() {
    Intent intent = new Intent(getActivity(), PersonController.class);
    Person person = data.getPersons().get(mapMarkers.get(marker).getPersonID());
    data.setSelectedPerson(person);
    startActivity(intent);
  }

  private void UpdateSearch() {
    Intent intent = new Intent(getActivity(), SearchController.class);
    startActivity(intent);
  }

  private void UpdateSettings() {
    Intent intent = new Intent(getActivity(), SettingsController.class);
    startActivity(intent);
  }

  private void UpdateMarker(Marker setMarker) {
    Event currentEvent = mapMarkers.get(setMarker);
    Person currentPerson = data.getPersons().get(currentEvent.getPersonID());
    String newName = currentPerson.getFirstName() + " " + currentPerson.getLastName();
    String eventInfo = currentEvent.getEventType() + ": " + currentEvent.getCity() + ", " + currentEvent.getCountry();
    String yearInfo = "(" + currentEvent.getYear() + ")";

    name.setText(newName);
    name.setVisibility(View.VISIBLE);
    name.setOnClickListener(onClickListener);

    event.setText(eventInfo);
    event.setVisibility(View.VISIBLE);
    event.setOnClickListener(onClickListener);

    year.setText(yearInfo);
    year.setVisibility(View.VISIBLE);
    year.setOnClickListener(onClickListener);

    if (currentPerson.getGender().toLowerCase().equals("m")) {
      icon.setImageDrawable(new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).color(0x3ec1d3).sizeDp(75));
    } else {
      icon.setImageDrawable(new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).color(0xff165d).sizeDp(75));

    }
    icon.setVisibility(View.VISIBLE);
    icon.setOnClickListener(onClickListener);

    marker = setMarker;
    data.setSelectedEvent(currentEvent);
    DrawLines();
    map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
  }

  private void PutMarkers(GoogleMap setMap) {
    marker = null;
    mapMarkers = new HashMap<>();
    mapEvents = data.getEvents();

    Map<String, MapColors> allMapColors = data.getEventColor();

    Log.d("Map Controller", "Checking gender events...");
    Log.d("Map Controller", "Map events before filter = " + mapEvents.size());

    UpdateMaleEvents();
    UpdateFemaleEvents();
    UpdatePaternalEvents();
    UpdateMaternalEvents();

    Log.d("Map Controller", "Map events AFTER filter = " + mapEvents.size());

    map = setMap;
    map.setMapType(IntermediateData.getInstance().getSettingsModel().getCurrMapType());

    map.setOnMarkerClickListener(marker -> {
      UpdateMarker(marker);
      return true;
    });
    for (Event e : mapEvents.values()) {
      LatLng position = new LatLng(e.getLatitude(), e.getLongitude());
      MapColors mapColor = allMapColors.get(e.getEventType().toLowerCase());

      if (mapColor == null) {
        mapColor = new MapColors(e.getEventType());
      }

      Marker mapMarker = map.addMarker(new MarkerOptions().position(position)
              .icon(BitmapDescriptorFactory.defaultMarker(mapColor.getColor()))
              .title(e.getEventType()));
      mapMarkers.put(mapMarker, e);

      if (data.getSelectedEvent() == e) {
        marker = mapMarker;
      }
    }

    if (marker != null && isEvent) {
      map.moveCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
      UpdateMarker(marker);
    }
  }

  private void ClearMap() {
    for (Marker m : mapMarkers.keySet()) {
      m.remove();
    }
  }

  private void ClearLines() {
    for (com.google.android.gms.maps.model.Polyline line : polylineList) {
      line.remove();
    }
    polylineList = new ArrayList<Polyline>();
  }

  private void DrawLines() {
    SettingsModel settingsModel = data.getSettingsModel();

    ClearLines();

    if (settingsModel.isStoryLines()) {
      DrawStoryLines();
    }
    if (settingsModel.isSpouseLines()) {
      DrawSpouseLines();
    }
    if (settingsModel.isFamilyLines()) {
      DrawFamilyLines();
    }
  }

  private void UpdateMaleEvents() {
    data.InitializeData();

    if (data.getSettingsModel().isMaleEvents()) {
      //male events are turned on
      Set<String> keys = maleEventHolder.keySet();
      for (String s : keys) {
        Log.d("Male Event added", s);
        mapEvents.put(s, maleEventHolder.get(s));
      }
      maleEventHolder.clear();
    } else {
      //male events are turned off
      List<Person> malePersons = data.getPaternalTree();
      for (Person p : malePersons) {
        List<Event> temp = data.getAllPersonEvents().get(p.getPersonID());
        for (Event e : temp) {
          mapEvents.remove(e.getEventID());
          maleEventHolder.put(e.getEventID(), e);
          Log.d("Male Event removed", String.valueOf(e.getEventID()));
        }
      }
    }
  }

  private void UpdateFemaleEvents() {
    data.InitializeData();

    if (data.getSettingsModel().isFemaleEvents()) {
      //female events are turned on
      Set<String> keys = femaleEventHolder.keySet();
      for (String s : keys) {
        Log.d("Female Event added", s);
        mapEvents.put(s, femaleEventHolder.get(s));
      }
      femaleEventHolder.clear();
    } else {
      //female events are turned off
      List<Person> femalePersons = data.getMaternalTree();
      for (Person p : femalePersons) {
        List<Event> temp = data.getAllPersonEvents().get(p.getPersonID());
        for (Event e : temp) {
          mapEvents.remove(e.getEventID());
          femaleEventHolder.put(e.getEventID(), e);
          Log.d("Female Event removed", String.valueOf(e.getEventID()));
        }
      }
    }
  }

  private void UpdatePaternalEvents() {
    data.InitializeData();

    if (data.getSettingsModel().isFatherSide()) {
      Set<String> keys = paternalEventHolder.keySet();
      for (String s : keys) {
        Log.d("Paternal Event added", s);
        mapEvents.put(s, paternalEventHolder.get(s));
      }
      paternalEventHolder.clear();
    } else {
      Set<String> paternalPersons = data.getPaternalAncestors();
      for (String s : paternalPersons) {
        List<Event> temp = data.getAllPersonEvents().get(s);
        for (Event e : temp) {
          mapEvents.remove(e.getEventID());
          paternalEventHolder.put(e.getEventID(), e);
          Log.d("Paternal Event removed", String.valueOf(e.getEventID()));
        }
      }
    }
  }

  private void UpdateMaternalEvents() {
    data.InitializeData();

    if (data.getSettingsModel().isMotherSide()) {
      Set<String> keys = maternalEventHolder.keySet();
      for (String s : keys) {
        Log.d("Maternal Event added", s);
        mapEvents.put(s, maternalEventHolder.get(s));
      }
      maternalEventHolder.clear();
    } else {
      Set<String> maternalPersons = data.getMaternalAncestors();
      for (String s : maternalPersons) {
        List<Event> temp = data.getAllPersonEvents().get(s);
        for (Event e : temp) {
          mapEvents.remove(e.getEventID());
          maternalEventHolder.put(e.getEventID(), e);
          Log.d("Maternal Event removed", String.valueOf(e.getEventID()));
        }
      }
    }
  }

  private void DrawStoryLines() {
    data.InitializeData();

    Event currentEvent = mapMarkers.get(marker);
    Person currentPerson = data.getPersons().get(currentEvent.getPersonID());
    List<Event> eventList = data.getAllPersonEvents().get(currentPerson.getPersonID());
    eventList = data.SortEventsByYear(eventList);

    if (! data.getFilter().ContainsEventType(currentEvent.getEventType())) {
      return;
    }

    int index = 0;
    while (index < eventList.size() - 1) {
      if (data.GetDisplayedEvents().containsValue(eventList.get(index))) {
        Event tempEvent = eventList.get(index);
        index++;

        LineHelper(tempEvent, eventList, index);

      }
    }
  }

  private void LineHelper(Event eventOne, List<Event> eventList, int index) {
    while (index < eventList.size()) {
      if (data.GetDisplayedEvents().containsValue(eventList.get(index))) {

        Event tempEvent = eventList.get(index);

        Polyline newLine = map.addPolyline(new PolylineOptions()
                .add(new LatLng(eventOne.getLatitude(), eventOne.getLongitude()),
                        new LatLng(tempEvent.getLatitude(), tempEvent.getLongitude()))
                .color(data.getSettingsModel().getStoryColor()));

        polylineList.add(newLine);
        return;
      }
      index++;
    }
  }

  private void DrawSpouseLines() {
    Event currentEvent = mapMarkers.get(marker);
    Person currentPerson = data.getPersons().get(currentEvent.getPersonID());
    List<Event> eventList = data.getAllPersonEvents().get(currentPerson.getSpouseID());
    eventList = data.SortEventsByYear(eventList);
    FilterModel filterModel = data.getFilter();

    if (filterModel.ContainsEventType(currentEvent.getEventType())) {
      for (int i = 0; i < eventList.size(); i++) {
        if (data.GetDisplayedEvents().containsValue(eventList.get(i))) {
          Event spouseEvent = eventList.get(i);

          Polyline newLine = map.addPolyline(new PolylineOptions()
                  .add(new LatLng(spouseEvent.getLatitude(), spouseEvent.getLongitude()),
                          new LatLng(currentEvent.getLatitude(), currentEvent.getLongitude()))
                  .color(data.getSettingsModel().getSpouseColor()));

          polylineList.add(newLine);
          return;
        }
      }
    }
  }

  private void DrawFamilyLines() {
    Event currentEvent = mapMarkers.get(marker);
    Person currentPerson = data.getPersons().get(currentEvent.getPersonID());

    FamilyLineHelper(currentPerson, currentEvent, 10);
  }

  private void FamilyLineHelper(Person sentPerson, Event focusedEvent, int generation) {
    if (sentPerson.getFatherID() != null) {
      FatherLineHelper(sentPerson, focusedEvent, generation);
    }
    if (sentPerson.getMotherID() != null) {
      MotherLineHelper(sentPerson, focusedEvent, generation);
    }
  }

  private void FatherLineHelper(Person sentPerson, Event focusedEvent, int generation) {
    List<Event> eventsList = data.getAllPersonEvents().get(sentPerson.getFatherID());
    eventsList = data.SortEventsByYear(eventsList);

    for (int i = 0; i < eventsList.size(); i++) {
      if (mapEvents.containsValue(eventsList.get(i))) {
        Event event = eventsList.get(i);

        Polyline newLine = map.addPolyline(new PolylineOptions()
                .add(new LatLng(focusedEvent.getLatitude(), focusedEvent.getLongitude()),
                        new LatLng(event.getLatitude(), event.getLongitude()))
                .color(data.getSettingsModel().getFamilyColor())
                .width(generation));
        polylineList.add(newLine);

        Person father = data.getPersons().get(sentPerson.getFatherID());
        FamilyLineHelper(father, event, generation / 2);
        return;
      }
    }

  }

  private void MotherLineHelper(Person sentPerson, Event focusedEvent, int generation) {
    List<Event> eventsList = data.getAllPersonEvents().get(sentPerson.getMotherID());
    eventsList = data.SortEventsByYear(eventsList);

    for (int i = 0; i < eventsList.size(); i++) {
      if (mapEvents.containsValue(eventsList.get(i))) {
        Event event = eventsList.get(i);

        Polyline newLine = map.addPolyline(new PolylineOptions()
                .add(new LatLng(focusedEvent.getLatitude(), focusedEvent.getLongitude()),
                        new LatLng(event.getLatitude(), event.getLongitude()))
                .color(data.getSettingsModel().getFamilyColor())
                .width(generation));
        polylineList.add(newLine);

        Person mother = data.getPersons().get(sentPerson.getMotherID());
        FamilyLineHelper(mother, event, generation / 2);
        return;
      }
    }
  }
}
