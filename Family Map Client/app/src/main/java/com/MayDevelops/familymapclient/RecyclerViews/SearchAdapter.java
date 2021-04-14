package com.MayDevelops.familymapclient.RecyclerViews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.MayDevelops.familymapclient.Controllers.EventController;
import com.MayDevelops.familymapclient.Controllers.PersonController;
import com.MayDevelops.familymapclient.Models.IntermediateData;
import com.MayDevelops.familymapclient.R;

import java.util.List;

import Models.Event;
import Models.Person;

public class SearchAdapter extends RecyclerView.Adapter<SearchHolder> {

  private List<Object> objectList;
  private Context context;
  private LayoutInflater inflater;

  public SearchAdapter(List<Object> objects, Context context) {
    this.context = context;
    this.objectList = objects;
    inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = inflater.inflate(R.layout.list_item_event, parent, false);
    return new SearchHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull SearchHolder holder, int position) {
    final Object currObject = objectList.get(position);
    if (currObject instanceof Person) {
      holder.GetLinearLayout().setOnClickListener(v -> PersonClicked((Person) currObject));
      holder.BindPerson(currObject);
    } else {
      holder.GetLinearLayout().setOnClickListener(v -> EventClicked((Event) currObject));
      holder.BindEvent(currObject);
    }
  }

  @Override
  public int getItemCount() {
    return objectList.size();
  }

  private void EventClicked(Event event) {
    Intent intent = new Intent(context, EventController.class);
    intent.putExtra("Event", "Event");
    IntermediateData.getInstance().setSelectedEvent(event);
    context.startActivity(intent);
  }

  private void PersonClicked(Person person) {
    Intent intent = new Intent(context, PersonController.class);
    IntermediateData.getInstance().setSelectedPerson(person);
    context.startActivity(intent);
  }
}
