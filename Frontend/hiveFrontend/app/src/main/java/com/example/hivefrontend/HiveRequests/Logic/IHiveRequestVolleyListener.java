package com.example.hivefrontend.HiveRequests.Logic;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface IHiveRequestVolleyListener {
    void getHiveRequests();

    void onHiveRequestSuccess(JSONArray response) throws JSONException;
    void onError();

    Context getRequestsContext();

    void acceptRequestLogic(JSONObject request, String status) throws JSONException;

    void onAcceptDenySuccess();
}
