package com.MayDevelops.familymapclient.Models;

import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;

public class SettingsModel {
  private boolean storyLines;
  private boolean familyLines;
  private boolean spouseLines;
  private boolean fatherSide;
  private boolean motherSide;
  private boolean maleEvents;
  private boolean femaleEvents;

  private final int storyColor;
  private final int familyColor;
  private final int spouseColor;
  private int currMapType;

  public SettingsModel() {
    storyLines = true;
    familyLines = true;
    spouseLines = true;
    fatherSide = true;
    motherSide = true;
    maleEvents = true;
    femaleEvents = true;
    storyColor = Color.BLUE;
    familyColor = Color.GREEN;
    spouseColor = Color.RED;
    currMapType = GoogleMap.MAP_TYPE_NORMAL;
  }


  public boolean isStoryLines() {
    return storyLines;
  }
  public boolean isFamilyLines() {
    return familyLines;
  }
  public boolean isSpouseLines() {
    return spouseLines;
  }
  public boolean isFatherSide() { return fatherSide; }
  public boolean isMotherSide() {
    return motherSide;
  }
  public boolean isMaleEvents() {
    return maleEvents;
  }
  public boolean isFemaleEvents() {
    return femaleEvents;
  }


  public void setStoryLines(boolean setStoryLines) {
    this.storyLines = setStoryLines;
  }
  public void setFamilyLines(boolean setFamilyLines) {
    this.familyLines = setFamilyLines;
  }
  public void setFatherSide(boolean setFatherSide) { this.fatherSide = setFatherSide; }
  public void setMotherSide(boolean setMotherSide) { this.motherSide = setMotherSide; }
  public void setMaleEvents(boolean setMaleEvents) { this.maleEvents = setMaleEvents; }
  public void setFemaleEvents(boolean setFemaleEvents) { this.femaleEvents = setFemaleEvents; }
  public void setSpouseLines(boolean spouseLines) {
    this.spouseLines = spouseLines;
  }

  public int getStoryColor() {
    return storyColor;
  }
  public int getFamilyColor() {
    return familyColor;
  }
  public int getSpouseColor() {
    return spouseColor;
  }
  public int getCurrMapType() {
    return currMapType;
  }

}
