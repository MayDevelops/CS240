package com.MayDevelops.familymapclient.Models;

import java.util.ArrayList;
import java.util.List;

public class FilterModel {


  private List<String> displayedEvents;
  private boolean fathersSide;
  private boolean mothersSide;
  private boolean males;
  private boolean females;

  public FilterModel() {
    displayedEvents = new ArrayList<>(IntermediateData.getInstance().getEventTypes());
    fathersSide = true;
    mothersSide = true;
    males = true;
    females = true;
  }

  public boolean ContainsEventType(String eventType) {
    eventType = eventType.toLowerCase();
    for (String s : displayedEvents) {
      if (s.toLowerCase().equals(eventType)) {
        return true;
      }
    }
    return false;
  }

  public boolean isMales() { return males; }
  public boolean isFemales() {
    return females;
  }
  public boolean isFathersSide() { return fathersSide; }
  public boolean isMothersSide() { return mothersSide; }

  public List<String> getDisplayedEvents() { return displayedEvents; }

  public void setFathersSide(boolean setFatherSide) { this.fathersSide = setFatherSide; }
  public void setMothersSide(boolean setMotherSide) { this.mothersSide = setMotherSide; }
  public void setMales(boolean setMales) { this.males = setMales; }
  public void setFemales(boolean setFemales) { this.females = setFemales; }
}
