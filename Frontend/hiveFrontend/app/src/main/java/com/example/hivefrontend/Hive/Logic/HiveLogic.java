package com.example.hivefrontend.Hive.Logic;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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
    public void onGetHiveNameSuccess(JSONArray response, String hiveName) {
        try {
            for(int i = 0; i < response.length(); i++) {
                JSONObject member = response.getJSONObject(i);
                if (member.getString("name").equals(hiveName)) {
                    // bio
                    // member count
                    // join status
                    Log.i("muchoguzto", "34");
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
