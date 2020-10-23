package com.example.hivefrontend.Login.Logic;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;

public interface ILoginVolleyListener {
    public Context getLoginContext();
    public void login(JSONArray response) throws JSONException;
}
