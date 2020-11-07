package com.example.hivefrontend.ui.search.Logic;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface ISearchVolleyListener  {

    void getHives();

    Context getSearchContext();

    void onError(VolleyError error);

    void onHiveRequestSuccess(JSONArray response);
}
