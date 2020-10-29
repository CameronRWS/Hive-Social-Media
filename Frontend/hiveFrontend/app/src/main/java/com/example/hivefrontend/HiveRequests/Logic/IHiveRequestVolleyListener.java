package com.example.hivefrontend.HiveRequests.Logic;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

public interface IHiveRequestVolleyListener {
    void getHiveRequests();

    void onHiveRequestSuccess(JSONArray response) throws JSONException;
    void onError();

    Context getRequestsContext();
}
