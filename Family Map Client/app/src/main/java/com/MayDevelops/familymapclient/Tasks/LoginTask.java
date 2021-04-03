package com.MayDevelops.familymapclient.Tasks;

import android.os.AsyncTask;

import com.MayDevelops.familymapclient.Models.IntermediateData;
import com.MayDevelops.familymapclient.ProxyServer;

import Requests.LoginRequest;
import Results.LoginResult;

public class LoginTask extends AsyncTask<LoginRequest, LoginResult, LoginResult> implements DataTask.DataContext {

  IntermediateData intermediateData = IntermediateData.getInstance();

  private String serverHost;
  private String ipAddress;
  private LoginContext context;

  public LoginTask(String setHostNumber, String setIP, LoginContext setLoginContext) {
    serverHost = setHostNumber;
    ipAddress = setIP;
    context = setLoginContext;
  }

  @Override
  protected LoginResult doInBackground(LoginRequest... loginRequest) {
    ProxyServer server = ProxyServer.initialize();
    LoginResult loginResult = server.Login(serverHost, ipAddress, loginRequest[0]);
    return loginResult;
  }

  @Override
  protected void onPostExecute(LoginResult loginResult) {
    if (loginResult.getSuccess()) {
      DataTask dataTask = new DataTask(serverHost, ipAddress, this);
      dataTask.execute(loginResult.getAuthtoken());
    } else {
      context.onExecuteComplete("Invalid Username or Password!");
      intermediateData.ClearData();
    }
  }

  public interface LoginContext {
    void onExecuteComplete(String message);
  }

  @Override
  public void onExecuteCompleteData(String message) {
    context.onExecuteComplete(message);
    intermediateData.PrintContents();
  }
}
