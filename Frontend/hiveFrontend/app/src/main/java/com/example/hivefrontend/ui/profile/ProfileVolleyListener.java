package com.example.hivefrontend.ui.profile;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;

/**
 * Interface implemented by ProfileLogic
 */
public interface ProfileVolleyListener {
    void onUserInfoSuccess(JSONArray response);
    void onUserInfoError(VolleyError error);
    void onHiveListSuccess(JSONArray response);
    void onHiveListError(VolleyError error);
    void pollenCountSuccess(String response);
    int getMemberCount();
    String getHiveDescrip();
    void onFetchHiveDescriptionSuccess(JSONArray response, String hiveName);
    void pollenCountError(VolleyError error);
    void onFetchMemberCountSuccess(JSONArray response, String hiveName);
    Context getProfileContext();
    int getUserId();
}
