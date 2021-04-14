package com.MayDevelops.familymapclient.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.MayDevelops.familymapclient.Controllers.PersonController;
import com.MayDevelops.familymapclient.R;

import java.util.List;

import Models.Event;
import Models.Person;

public class PersonModel extends BaseExpandableListAdapter {

  private final IntermediateData data = IntermediateData.getInstance();

  private Context context;
  private List<String> headers;
  private List<Event> eventList;
  private List<Person> personList;
  private Person person;

  private TextView lineOne;
  private TextView lineTwo;
  private ImageView listIcon;


  public PersonModel(PersonController personController, List<String> headers, List<Event> eventsArrayList, List<Person> relatives, Person person) {
    this.context = personController;
    this.headers = headers;
    this.eventList = eventsArrayList;
    this.personList = relatives;
    this.person = person;
  }

  @Override
  public int getGroupCount() {
    return headers.size();
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    if (groupPosition == 0) {
      return eventList.size();
    } else {
      return personList.size();
    }
  }

  @Override
  public Object getGroup(int groupPosition) {
    if (groupPosition == 0) {
      return eventList;
    } else {
      return personList;
    }
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    if (groupPosition == 0) {
      return eventList.get(childPosition);
    } else {
      return personList.get(childPosition);
    }
  }

  @Override
  public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
    String headerTitle = headers.get(groupPosition);

    if (convertView == null) {
      LayoutInflater inflater = (LayoutInflater) this.context
              .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.list_header_event, null);
    }

    TextView header = convertView.findViewById(R.id.event_header);
    header.setText(headerTitle);

    return convertView;
  }

  @Override
  public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
    if (convertView == null) {
      LayoutInflater inflater = (LayoutInflater) context
              .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.list_item_event, null);
    }

    lineOne = convertView.findViewById(R.id.event_list_info);
    lineTwo = convertView.findViewById(R.id.event_list_person);
    listIcon = convertView.findViewById(R.id.list_item_icon);

    if (groupPosition == 0) {
      Event event = (Event) getChild(groupPosition, childPosition);

      listIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.map_pointer_icon));
      Update(event, null);

    } else {
      Person person = (Person) getChild(groupPosition, childPosition);

      if (person.getGender().toLowerCase().equals("m")) {
        listIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.male_icon));
      } else {
        listIcon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.female_icon));
      }
      Update(null, person);
    }
    return convertView;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }

  private void Update(Event events, Person persons) {
    if (persons == null) {
      String eventInfo = events.getEventType() + ", " + events.getCity() + ", " + events.getCountry() + " " + events.getYear();
      lineOne.setText(eventInfo);
      Person person = data.getPersons().get(events.getPersonID());
      String personInfo = person.getFirstName() + " " + person.getLastName();
      lineTwo.setText(personInfo);
    } else {
      String personInfo = persons.getFirstName() + " " + persons.getLastName();
      lineOne.setText(personInfo);
      lineTwo.setText(GetRelationship(persons));
    }
  }

  private String GetRelationship(Person persons) {
    if (person.getSpouseID().equals(persons.getPersonID())) {
      return "Spouse";
    }

    if (persons.getFatherID() != null && persons.getMotherID() != null) {
      if (persons.getFatherID().equals(person.getPersonID()) ||
              persons.getMotherID().equals(person.getPersonID())) {
        return "Child";
      }
    }

    if (person.getMotherID() != null && person.getMotherID() != null) {
      if (person.getFatherID().equals(persons.getPersonID())) {
        return "Father";
      } else if (person.getMotherID().equals(persons.getPersonID())) {
        return "Mother";
      }
    }
    return "Error";
  }

}
