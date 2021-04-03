package com.MayDevelops.familymapclient.Controllers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MayDevelops.familymapclient.Tasks.LoginTask;
import com.MayDevelops.familymapclient.Tasks.RegisterTask;
import com.MayDevelops.familymapclient.R;

import Requests.LoginRequest;
import Requests.RegisterRequest;


public class LoginController extends Fragment implements LoginTask.LoginContext, RegisterTask.RegisterContext{
  private RegisterRequest registerRequest;
  private LoginRequest loginRequest;

  private TextWatcher watcher;

  private EditText serverPort;
  private EditText ipAddress;
  private EditText username;
  private EditText password;
  private EditText firstName;
  private EditText lastName;
  private EditText email;

  private Button maleButton;
  private Button femaleButton;

  private Button loginButton;
  private Button registerButton;

  public boolean loggedIn;



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    registerRequest = new RegisterRequest();
    loginRequest = new LoginRequest();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_login, container, false);
    watcher = new Enabler();

    ipAddress = (EditText) view.findViewById(R.id.ipInput);
    ipAddress.addTextChangedListener(watcher);

    serverPort = view.findViewById(R.id.portInput);
    serverPort.addTextChangedListener(watcher);

    username = view.findViewById(R.id.usernameInput);
    username.addTextChangedListener(watcher);

    password = view.findViewById(R.id.passwordInput);
    password.addTextChangedListener(watcher);

    firstName = view.findViewById(R.id.firstNameInput);
    firstName.addTextChangedListener(watcher);

    lastName = view.findViewById(R.id.lastNameInput);
    lastName.addTextChangedListener(watcher);

    email = view.findViewById(R.id.emailInput);
    email.addTextChangedListener(watcher);

    maleButton = view.findViewById(R.id.maleButton);
    maleButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        registerRequest.setGender("M");
        Validate();
      }
    });

    femaleButton = view.findViewById(R.id.femaleButton);
    femaleButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        registerRequest.setGender("F");
        Validate();
      }
    });

    loginButton = view.findViewById(R.id.loginButton);
    registerButton = view.findViewById(R.id.registerButton);
    Validate();

    loginButton.setOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        loginRequest.setUsername(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());
        LoginTask loginTask = new LoginTask(ipAddress.getText().toString(),
                serverPort.getText().toString(),
                LoginController.this);

        loginTask.execute(loginRequest);
      }
    });

    registerButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v)
      {

        registerRequest.setUsername(username.getText().toString());
        registerRequest.setPassword(password.getText().toString());
        registerRequest.setEmail(email.getText().toString());
        registerRequest.setFirstName(firstName.getText().toString());
        registerRequest.setLastName(lastName.getText().toString());

        RegisterTask regTask = new RegisterTask(ipAddress.getText().toString(),
                serverPort.getText().toString(),
                LoginController.this);

        regTask.execute(registerRequest);
      }
    });
    return view;
  }



  @Override
  public void onExecuteComplete(String message) {
    Toast toast = new Toast(getContext());
    toast.setText(message);
    toast.setDuration(Toast.LENGTH_LONG);
    toast.show();

    if(message.startsWith("W")) {
      loggedIn = true;
    }

  }

  private void Validate() {
    if (validiateRegisterButon()) {
      registerButton.setEnabled(false);
    } else {
      registerButton.setEnabled(true);
    }

    if (validateLoginButton()) {
      loginButton.setEnabled(false);
    } else {
      loginButton.setEnabled(true);
    }
  }

  private boolean validiateRegisterButon() {
    return TextUtils.isEmpty(serverPort.getText()) ||
            TextUtils.isEmpty(ipAddress.getText()) ||
            TextUtils.isEmpty(username.getText()) ||
            TextUtils.isEmpty(password.getText()) ||
            TextUtils.isEmpty(email.getText()) ||
            TextUtils.isEmpty(firstName.getText()) ||
            TextUtils.isEmpty(lastName.getText()) ||
            registerRequest.getGender() == null;
  }

  private boolean validateLoginButton() {
    return TextUtils.isEmpty(serverPort.getText()) ||
            TextUtils.isEmpty(ipAddress.getText()) ||
            TextUtils.isEmpty(username.getText()) ||
            TextUtils.isEmpty(password.getText());
  }

  private class Enabler implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      Validate();
    }

    @Override
    public void afterTextChanged(Editable s) {}
  }
}