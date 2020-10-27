package com.example.hivefrontend.Login.Logic;

import android.content.Context;

import org.json.JSONArray;

public interface ILoginVolleyListener {

    public void onLoginUserSuccess(JSONArray response);
    Context getLoginContext();
}
