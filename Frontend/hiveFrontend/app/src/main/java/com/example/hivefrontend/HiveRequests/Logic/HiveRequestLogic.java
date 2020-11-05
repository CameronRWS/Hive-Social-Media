package com.example.hivefrontend.HiveRequests.Logic;

import android.content.Context;
import android.util.Log;

import com.example.hivefrontend.HiveRequests.HiveRequestsActivity;
import com.example.hivefrontend.HiveRequests.IHiveRequestView;
import com.example.hivefrontend.HiveRequests.Server.HiveRequestServerRequest;
import com.example.hivefrontend.HiveRequests.Server.IHiveRequestServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HiveRequestLogic implements IHiveRequestVolleyListener {

    private IHiveRequestView view;
    private IHiveRequestServerRequest server;
    public HiveRequestLogic(IHiveRequestView view, IHiveRequestServerRequest server) {
        this.view = view;
        this.server=server;
        server.addVolleyListener(this);
    }

    @Override
    public void getHiveRequests() {
        server.getHiveRequests();
    }

    @Override
    public void onHiveRequestSuccess(JSONArray response) throws JSONException {


        for(int i = 0; i<response.length(); i++){
            if(isActive(response.getJSONObject(i))){
                view.addToRequests(response.getJSONObject(i));
            }
        }

    }

    public boolean isActive(JSONObject hiveRequest) throws JSONException {
        return hiveRequest.getBoolean("isActive");
    }

    @Override
    public void onError() {
        view.clearData();
        getHiveRequests();
    }

    @Override
    public Context getRequestsContext() {
        return view.getRequestsContext();
    }

    @Override
    public void acceptRequestLogic(JSONObject request, String status) throws JSONException {
        server.acceptRequest(request, status);
    }

    @Override
    public void onAcceptDenySuccess() {
        Log.i("requests success ", " woohoo ");
        view.clearData();
        getHiveRequests();
    }
}
