package com.example.hivefrontend.HiveCreation.Logic;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Interface implemented by HiveCreationLogic
 */
public interface IHiveCreationVolleyListener {

    void onError(VolleyError error);

    Context getPostContext();

    void onHiveCreationSuccess(JSONObject response) throws JSONException;

    void createHive(JSONObject hive);

    void onMemberCreationSuccess(JSONObject response);

    int getUserId();
}
