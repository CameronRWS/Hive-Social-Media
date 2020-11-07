package com.example.hivefrontend.HiveRequests.Server;

import com.example.hivefrontend.HiveRequests.Logic.HiveRequestLogic;
import com.example.hivefrontend.HiveRequests.Logic.IHiveRequestVolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Interface implemented by HiveRequestServerRequest
 */
public interface IHiveRequestServerRequest {
    void addVolleyListener(IHiveRequestVolleyListener logic);

    void getHiveRequests();

    void handleRequest(JSONObject request, String status) throws JSONException;
}
