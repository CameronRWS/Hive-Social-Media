package com.example.hivefrontend.Register.Logic;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The interface tht the RegisterLogic implements.
 */
public interface IRegisterVolleyListener {

    public JSONObject createUser();
    public Context getRegisterContext();
    public void onRegisterUserSuccess(JSONObject response) throws JSONException;
    public boolean isAvailable(JSONArray response) throws JSONException;
}
