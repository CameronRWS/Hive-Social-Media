package com.example.hivefrontend.HiveRequests.Logic;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Interface implemented by HiveRequestLogic
 */
public interface IHiveRequestVolleyListener {
    void getHiveRequests();

    void onHiveRequestSuccess(JSONArray response) throws JSONException;
    void onError();

    Context getRequestsContext();

    void handleRequestLogic(JSONObject request, String status) throws JSONException;

    void onAcceptDenySuccess();
}
