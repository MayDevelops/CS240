package com.MayDevelops.familymapclient.RecyclerViews;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MayDevelops.familymapclient.Models.IntermediateData;
import com.MayDevelops.familymapclient.R;

import Models.Event;
import Models.Person;

public class SearchHolder extends RecyclerView.ViewHolder {

  private View convertView;

  private LinearLayout linearLayout;
  private TextView firstLine;
  private TextView description;
  private ImageView icon;


  public SearchHolder(@NonNull View itemView) {
    super(itemView);
    firstLine = itemView.findViewById(R.id.event_list_info);
    description = itemView.findViewById(R.id.event_list_person);
    icon = itemView.findViewById(R.id.list_item_icon);
    linearLayout = itemView.findViewById(R.id.linear_layout_click_area);
    linearLayout.setClickable(true);
    convertView = itemView;
  }

  public LinearLayout GetLinearLayout() {
    return linearLayout;
  }

  public void BindEvent(Object object) {

    final Event event = (Event) object;
    String eventInfo = event.getEventType() + ", " + event.getCity() + ", "
            + event.getCountry() + " " + event.getYear();
    firstLine.setText(eventInfo);

    IntermediateData data = IntermediateData.getInstance();
    Person person = data.getPersons().get(event.getPersonID());
    String personInfo = person.getFirstName() + " " + person.getLastName();
    description.setText(personInfo);
    icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.map_pointer_icon));

  }

  public void BindPerson(Object object) {
    Person person = (Person) object;
    String personInfo = person.getFirstName() + " " + person.getLastName();
    firstLine.setText(personInfo);
    description.setVisibility(View.INVISIBLE);
    if (person.getGender().toLowerCase().equals("m")) {
      icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.male_icon));
    } else {
      icon.setImageDrawable(convertView.getResources().getDrawable(R.drawable.female_icon));
    }
  }
}
