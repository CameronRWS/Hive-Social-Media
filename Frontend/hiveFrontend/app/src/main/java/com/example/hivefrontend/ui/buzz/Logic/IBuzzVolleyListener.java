package com.example.hivefrontend.ui.buzz.Logic;

import android.content.Context;

import org.json.JSONArray;

public interface IBuzzVolleyListener {

    public void onGetHivesSuccess(JSONArray response);
    Context getBuzzContext();

}
