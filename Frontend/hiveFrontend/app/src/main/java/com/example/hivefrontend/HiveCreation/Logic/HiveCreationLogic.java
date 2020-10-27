package com.example.hivefrontend.HiveCreation.Logic;

import android.content.Context;
import android.content.Intent;

import com.android.volley.VolleyError;
import com.example.hivefrontend.HiveCreation.IHiveCreationView;
import com.example.hivefrontend.HiveCreation.Server.IHiveCreationServerRequest;
import com.example.hivefrontend.MainActivity;
import com.example.hivefrontend.PostDetails.IPostView;
import com.example.hivefrontend.PostDetails.Network.IPostServerRequest;
import com.example.hivefrontend.ui.buzz.BuzzFragment;

import org.json.JSONException;
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
    public void onHiveCreationSuccess(JSONObject response) throws JSONException {
        //add the current user to the hive
        int hiveId = response.getInt("hiveId");
        server.addMembership(hiveId);
        //go back to main screen
        view.goHome();

    }

    @Override
    public void createHive(JSONObject hive){
        server.createHive(hive);
    }

    @Override
    public void onMemberCreationSuccess(JSONObject response) {

    }
}
