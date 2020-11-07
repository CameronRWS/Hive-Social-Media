package com.example.hivefrontend.ui.search.Logic;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hivefrontend.ui.search.ISearchView;
import com.example.hivefrontend.ui.search.Server.ISearchServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchLogic implements ISearchVolleyListener {


    private ISearchView s;
    private ISearchServerRequest server;

    public SearchLogic(ISearchView s, ISearchServerRequest req){
        this.s = s;
        this.server= req;
        server.addVolleyListener(this);
    }

    public void getHives(){
        server.getUserHives(s.getUserId());
    }

    @Override
    public Context getSearchContext() {
        return s.getSearchContext();
    }

    @Override
    public void onError(VolleyError error) {
        Log.i("volleyAppError", "Error: " + error.getMessage());
        Log.i("volleyAppError", "VolleyError: " + error);
    }

    @Override
    public void onHiveRequestSuccess(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject member = response.getJSONObject(i); //should return user,hive pair
                Integer hiveId = (Integer) member.getJSONObject("hive").getInt("hiveId");
                s.addToUserHives(hiveId);
                Log.i(" hive of this user : ", " " + hiveId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("jsonAppError", e.toString());
        }
    }
}
