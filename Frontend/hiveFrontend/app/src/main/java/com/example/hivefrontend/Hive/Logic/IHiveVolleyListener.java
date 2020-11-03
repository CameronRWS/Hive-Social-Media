package com.example.hivefrontend.Hive.Logic;

import android.content.Context;

import org.json.JSONArray;

public interface IHiveVolleyListener {
    Context getHiveContext();
    public String onGetUserIdSuccess(JSONArray response, String email);
    public void onGetHiveSuccess(JSONArray response);
}
