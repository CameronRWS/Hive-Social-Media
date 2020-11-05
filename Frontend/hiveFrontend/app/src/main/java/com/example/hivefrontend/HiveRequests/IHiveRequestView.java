package com.example.hivefrontend.HiveRequests;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;

public interface IHiveRequestView {

    void setRequests(ArrayList<JSONObject> requests);
    void addToRequests(JSONObject request);

    //void acceptRequest(int position);
    //void denyRequest(int position);

    Context getRequestsContext();

    void clearData();
}
