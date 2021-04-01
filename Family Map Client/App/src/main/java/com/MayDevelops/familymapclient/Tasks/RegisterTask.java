package com.MayDevelops.familymapclient.Tasks;

import android.os.AsyncTask;

import com.MayDevelops.familymapclient.Models.IntermediateData;
import com.MayDevelops.familymapclient.ProxyServer;

import Requests.RegisterRequest;
import Results.RegisterResult;

public class RegisterTask extends AsyncTask<RegisterRequest, RegisterResult, RegisterResult> implements DataTask.DataContext{
  IntermediateData intermediateData = IntermediateData.getInstance();

  private String serverHost;
  private String ipAddress;
  private RegisterContext context;

  public RegisterTask(String setServer, String setIP, RegisterContext rc) {
    serverHost = setServer;
    ipAddress = setIP;
    context = rc;
  }

  @Override
  protected RegisterResult doInBackground(RegisterRequest... registerRequests) {
    ProxyServer server = ProxyServer.initialize();
    RegisterResult registerResult = server.Register(serverHost, ipAddress, registerRequests[0]);
    return registerResult;
  }

  @Override
  public void onPostExecute(RegisterResult registerResult) {
    if(registerResult.getSuccess()) {
      DataTask dataTask = new DataTask(serverHost,ipAddress,this);
      dataTask.execute(registerResult.getAuthtoken());
    } else {
      context.onExecuteComplete("Unable to Register new user!");
    }
  }

  @Override
  public void onExecuteCompleteData(String message) {
    context.onExecuteComplete(message);
  }

  public interface RegisterContext {
    void onExecuteComplete(String message);
  }
}
