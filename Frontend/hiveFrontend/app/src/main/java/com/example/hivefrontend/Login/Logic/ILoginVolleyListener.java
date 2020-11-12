package com.example.hivefrontend.Login.Logic;

import android.content.Context;

import org.json.JSONArray;

/**
 * The interface which LoginLogic implements.
 */
public interface ILoginVolleyListener {

    public void onLoginUserSuccess(JSONArray response);
    Context getLoginContext();
}
