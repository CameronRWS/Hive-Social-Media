package com.example.hivefrontend.HiveCreation.Logic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hivefrontend.HiveCreation.IHiveCreationView;
import com.example.hivefrontend.HiveCreation.Server.IHiveCreationServerRequest;
import com.example.hivefrontend.MainActivity;
import com.example.hivefrontend.PostDetails.IPostView;
import com.example.hivefrontend.PostDetails.Network.IPostServerRequest;
import com.example.hivefrontend.ui.buzz.BuzzFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles the logic of HiveCreation
 */
public class HiveCreationLogic implements IHiveCreationVolleyListener {


    IHiveCreationView view;
    IHiveCreationServerRequest server;

    /**
     * Creates an instance of HiveCreationLogic from the given IHiveCreationView and IHiveCreationServerRequest
     * @param v The IHiveCreationView to use
     * @param server The IHiveCreationServerRequest to use
     */
    public HiveCreationLogic(IHiveCreationView v, IHiveCreationServerRequest server){
        this.view = v;
        this.server=server;
        server.addVolleyListener(this);
    }

    /**
     * Returns the current user's user id
     * @return The current user's user id
     */
    public int getUserId(){
        return view.getUserId();
    }

    /**
     * Called when a request fails due to a VolleyError
     * @param error The VolleyError received
     */
    @Override
    public void onError(VolleyError error) {
        Log.i("volleyAppError", "Error: " + error.getMessage());
        Log.i("volleyAppError", "VolleyError: " + error);
    }

    /**
     * Returns the context for the IHiveCreationView associated with this HiveCreationLogic
     * @return The context for the associated IHiveCreationView
     */
    @Override
    public Context getPostContext() {
        return view.getViewContext();
    }

    /**
     * Handles a successful hive creation
     * @param response Response received from the server request
     * @throws JSONException
     */
    @Override
    public void onHiveCreationSuccess(JSONObject response) throws JSONException {
        //add the current user to the hive
        int hiveId = response.getInt("hiveId");
        server.addMembership(hiveId);
    }

    /**
     * Calls the server request class to request hive creation using the provided JSONObject
     * @param hive The JSONObject to post to the server
     */
    @Override
    public void createHive(JSONObject hive){
        server.createHive(hive);
    }

    /**
     * Handles a successful member creation by taking the user back to the home screen
     * @param response The response received from the server
     */
    @Override
    public void onMemberCreationSuccess(JSONObject response) {
        //go back to main screen
        view.goHome();
    }
}
