package com.example.hivefrontend.ui.buzz.Logic;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

public interface IBuzzVolleyListener {

    public void onGetHivesSuccess(JSONArray response);
    Context getBuzzContext();
    public JSONObject createBuzzPost();
    int getUserId();

}
