package com.MayDevelops.familymapclient.Models;

import android.graphics.Color;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_VIOLET;


public class MapColors extends Color {
  private float color;

  public MapColors(String eventType) {

    if (eventType.toLowerCase().equals("birth")) {
      color = HUE_CYAN;
    } else if (eventType.toLowerCase().equals("death")) {
      color = 50f;
    } else if (eventType.toLowerCase().equals("marriage")) {
      color = HUE_VIOLET;
    } else {
      color = Math.abs(eventType.toLowerCase().hashCode() % 360);
    }
  }

  public void setColor(int setColor) {this.color = setColor; }

  public float getColor() { return color; }
}
