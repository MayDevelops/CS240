package com.MayDevelops.familymapclient.Controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.MayDevelops.familymapclient.Models.IntermediateData;
import com.MayDevelops.familymapclient.Models.SettingsModel;
import com.MayDevelops.familymapclient.R;


public class SettingsController extends AppCompatActivity {

  private Switch lifeStory;
  private Switch familyTree;
  private Switch spouseLines;
  private Switch fatherSide;
  private Switch motherSide;
  private Switch maleEvents;
  private Switch femaleEvents;

  private Button logout;

  private SettingsModel settingsModel;
  private IntermediateData data = IntermediateData.getInstance();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    lifeStory = findViewById(R.id.life_switch);
    lifeStory.setChecked(data.getSettingsModel().isStoryLines());
    familyTree = findViewById(R.id.tree_switch);
    familyTree.setChecked(data.getSettingsModel().isFamilyLines());
    spouseLines = findViewById(R.id.spouse_switch);
    spouseLines.setChecked(data.getSettingsModel().isSpouseLines());
    fatherSide = findViewById(R.id.fatherSideFilterSwitch);
    fatherSide.setChecked(data.getSettingsModel().isFatherSide());
    motherSide = findViewById(R.id.motherSideFilterSwitch);
    motherSide.setChecked(data.getSettingsModel().isMotherSide());
    maleEvents = findViewById(R.id.maleEventFilterSwitch);
    maleEvents.setChecked(data.getSettingsModel().isMaleEvents());
    femaleEvents = findViewById(R.id.femaleEventFilterSwitch);
    femaleEvents.setChecked(data.getSettingsModel().isFemaleEvents());

    logout = findViewById(R.id.logout_button_settings);
    logout.setLinksClickable(true);

    SharedPreferences shared = getSharedPreferences("familyShared", MODE_PRIVATE);
    SharedPreferences.Editor editor = shared.edit();

    lifeStory.setOnCheckedChangeListener((buttonView, isChecked) -> {
      data.getSettingsModel().setStoryLines(isChecked);
      editor.putBoolean("lifeStory", isChecked);
      editor.commit();
    });

    familyTree.setOnCheckedChangeListener((buttonView, isChecked) -> {
      data.getSettingsModel().setFamilyLines(isChecked);
      editor.putBoolean("familyTree", isChecked);
      editor.commit();
    });

    spouseLines.setOnCheckedChangeListener((buttonView, isChecked) -> {
      data.getSettingsModel().setSpouseLines(isChecked);
      editor.putBoolean("spouseLines", isChecked);
      editor.commit();
    });

    fatherSide.setOnCheckedChangeListener(((buttonView, isChecked) -> {
      data.getSettingsModel().setFatherSide(isChecked);
      editor.putBoolean("fatherSide", isChecked);
      editor.commit();
    }));

    motherSide.setOnCheckedChangeListener(((buttonView, isChecked) -> {
      data.getSettingsModel().setMotherSide(isChecked);
      editor.putBoolean("motherSide", isChecked);
      editor.commit();
    }));

    maleEvents.setOnCheckedChangeListener(((buttonView, isChecked) -> {
      data.getSettingsModel().setMaleEvents(isChecked);
      editor.putBoolean("maleEvents", isChecked);
      editor.commit();
    }));

    femaleEvents.setOnCheckedChangeListener(((buttonView, isChecked) -> {
      data.getSettingsModel().setFemaleEvents(isChecked);
      editor.putBoolean("femaleEvents", isChecked);
      editor.commit();
    }));

    logout.setOnClickListener(v -> {
      Intent intent = new Intent(getBaseContext(), MainController.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivityForResult(intent, 0);
    });


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
