package com.example.hivefrontend.HiveCreation.Server;


import com.example.hivefrontend.HiveCreation.Logic.IHiveCreationVolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Interface implemented by HiveCreationServerRequest
 */
public interface IHiveCreationServerRequest {
    void addVolleyListener(IHiveCreationVolleyListener l);

    void createHive(JSONObject hive);

    void addMembership(int hiveId) throws JSONException;
}
