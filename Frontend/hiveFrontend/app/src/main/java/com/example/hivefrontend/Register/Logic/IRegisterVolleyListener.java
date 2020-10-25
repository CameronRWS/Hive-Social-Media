package com.example.hivefrontend.Register.Logic;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IRegisterVolleyListener {

    public JSONObject createUser();
    public Context getRegisterContext();
    public void onRegisterUserSuccess(JSONObject response);
}
