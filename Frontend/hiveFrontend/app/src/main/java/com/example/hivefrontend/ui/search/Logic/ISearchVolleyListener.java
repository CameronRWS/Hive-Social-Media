package com.example.hivefrontend.ui.search.Logic;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Interface implemented by SearchLogic
 */
public interface ISearchVolleyListener  {

    void getHives();

    Context getSearchContext();

    void onError(VolleyError error);

    void onHiveRequestSuccess(JSONArray response);

    void onGetOtherHivesRequestSuccess(JSONArray response) throws JSONException;


}
