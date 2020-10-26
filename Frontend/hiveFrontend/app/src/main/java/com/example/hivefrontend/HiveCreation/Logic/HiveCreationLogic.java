package com.example.hivefrontend.HiveCreation.Logic;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.hivefrontend.HiveCreation.IHiveCreationView;
import com.example.hivefrontend.HiveCreation.Server.IHiveCreationServerRequest;
import com.example.hivefrontend.PostDetails.IPostView;
import com.example.hivefrontend.PostDetails.Network.IPostServerRequest;

import org.json.JSONObject;

public class HiveCreationLogic implements IHiveCreationVolleyListener {


    IHiveCreationView view;
    IHiveCreationServerRequest server;

    public HiveCreationLogic(IHiveCreationView v, IHiveCreationServerRequest server){
        this.view = v;
        this.server=server;
        server.addVolleyListener(this);
    }

    @Override
    public void onError(VolleyError error) {

    }

    @Override
    public Context getPostContext() {
        return null;
    }

    @Override
    public void onHiveCreationSuccess(JSONObject response, JSONObject hive) {

    }

    @Override
    public void createHive(){
        server.createHive();
    }
}
