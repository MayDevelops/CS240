package com.MayDevelops.familymapclient.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.MayDevelops.familymapclient.R;

public class MainController extends AppCompatActivity {
  private final String TAG = "MainActivity";

  LoginController loginController;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    if(savedInstanceState == null) {
      getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
              new LoginController()).commit();
    }

    loginController = (LoginController) getSupportFragmentManager().findFragmentById(R.id.fragment_login);

  }
}