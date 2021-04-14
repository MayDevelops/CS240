package com.MayDevelops.familymapclient.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.MayDevelops.familymapclient.R;

public class EventController extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_event);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    String arguments = getIntent().getExtras().getString("Event");

    FragmentManager supportFragmentManager = getSupportFragmentManager();
    Fragment mapFragment = new MapController(arguments);
    FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

    fragmentTransaction.add(R.id.map_fragment, mapFragment);
    fragmentTransaction.addToBackStack(null);

    fragmentTransaction.commit();

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

}
