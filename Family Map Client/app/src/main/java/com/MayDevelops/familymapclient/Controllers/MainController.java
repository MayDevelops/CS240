package com.MayDevelops.familymapclient.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.MayDevelops.familymapclient.Models.IntermediateData;
import com.MayDevelops.familymapclient.Models.SettingsModel;
import com.MayDevelops.familymapclient.R;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;


public class MainController extends AppCompatActivity implements LoginController.LoginListener {

  private FragmentManager fragmentManager = getSupportFragmentManager();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    IntermediateData data = IntermediateData.getInstance();
    SharedPreferences shared = getSharedPreferences("familyShared", MODE_PRIVATE);
    SettingsModel settings = data.getSettingsModel();
    if (shared != null) {
      settings.setStoryLines(shared.getBoolean("lifeStory", true));
      settings.setFamilyLines(shared.getBoolean("familyTree", true));
      settings.setSpouseLines(shared.getBoolean("spouseLines", true));
      settings.setFatherSide(shared.getBoolean("fatherSide", true));
      settings.setMotherSide(shared.getBoolean("motherSide", true));
      settings.setMaleEvents(shared.getBoolean("maleEvents", true));
      settings.setFemaleEvents(shared.getBoolean("femaleEvents", true));

    }

    Iconify.with(new FontAwesomeModule());

    setContentView(R.layout.activity_main);
    Fragment loginController = fragmentManager.findFragmentById(R.id.fragment_container);

    if ((getIntent().getExtras() != null)) {

      Fragment mapController = new MapController();
      FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

      fragmentTransaction.add(R.id.fragment_container, mapController).commit();
    } else if (loginController == null) {
      loginController = new LoginController();
      ((LoginController) loginController).SetLoginListener(this);
      fragmentManager.beginTransaction().add(R.id.fragment_container, loginController).commit();
    }
  }

  @Override
  public void SwapToMap() {
    Fragment mapController = new MapController();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    fragmentTransaction.replace(R.id.fragment_container, mapController).commit();
    fragmentTransaction.addToBackStack(null);
  }


}


