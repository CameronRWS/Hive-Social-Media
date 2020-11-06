package com.example.hivefrontend.Hive.Logic;

import android.content.Context;

import org.json.JSONArray;

public interface IHiveVolleyListener {
    Context getHiveContext();
    public void onGetHiveNameSuccess(JSONArray response, String hiveName);
}
