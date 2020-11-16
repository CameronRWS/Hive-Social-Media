package com.example.hivefrontend.EditProfile.Logic;

import android.content.Context;

import org.json.JSONArray;

public interface IEPVolleyListener {
    public Context getEPContext();
    public void onSaveSuccess(JSONArray response);
}
