package com.example.hivefrontend.Hive.Logic;

import android.content.Context;

import com.example.hivefrontend.Hive.IHiveView;
import com.example.hivefrontend.Hive.Network.IHiveServerRequest;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HiveLogic implements IHiveVolleyListener {
    IHiveView hiveView;
    IHiveServerRequest server;

    public HiveLogic(IHiveView hv, IHiveServerRequest hsr) {
        this.hiveView = hv;
        this.server = hsr;
        server.addVolleyListener(this);
    }

    @Override
    public Context getHiveContext() {
        return hiveView.getHiveContext();
    }

    @Override
    public String onGetUserIdSuccess(JSONArray response, String email) {
        String userId = "";
        try {
            for(int i = 0; i < response.length(); i++) {
                JSONObject member = response.getJSONObject(i);
                if (email.compareTo(member.getString("email")) == 0) {
                   return member.getString("userId");
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return userId;
    }

    @Override
    public void onGetHiveSuccess(JSONArray response) {
        try {
            for(int i = 0; i < response.length(); i++) {
                JSONObject member = response.getJSONObject(i);

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
