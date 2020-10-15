package com.example.hivefrontend.ui.profile;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface ProfileVolleyListener {
    public void onUserInfoSuccess(JSONArray response);
    public void onUserInfoError(VolleyError error);
    public void onHiveListSuccess(JSONArray response);
    public void onHiveListError(VolleyError error);
    public void pollenCountSuccess(String response);
    public void pollenCountError(VolleyError error);
    public Context getProfileContext();
    public int getUserId();
}
