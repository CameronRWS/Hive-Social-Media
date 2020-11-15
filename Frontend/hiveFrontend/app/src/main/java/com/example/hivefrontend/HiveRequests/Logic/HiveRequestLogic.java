package com.example.hivefrontend.HiveRequests.Logic;

import android.content.Context;
import android.util.Log;

import com.example.hivefrontend.HiveRequests.HiveRequestsActivity;
import com.example.hivefrontend.HiveRequests.IHiveRequestView;
import com.example.hivefrontend.HiveRequests.Server.HiveRequestServerRequest;
import com.example.hivefrontend.HiveRequests.Server.IHiveRequestServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Handles the logic for HiveRequestsActivity
 */
public class HiveRequestLogic implements IHiveRequestVolleyListener {

    private IHiveRequestView view;
    private IHiveRequestServerRequest server;

    /**
     * Creates a HiveRequestLogic and sets local variables for the given IHiveRequestView and IHiveRequestServerRequest
     * @param view The IHiveRequestView to use
     * @param server The IHiveRequestServerRequest to use
     */
    public HiveRequestLogic(IHiveRequestView view, IHiveRequestServerRequest server) {
        this.view = view;
        this.server=server;
        server.addVolleyListener(this);
    }

    /**
     * Calls the server request class to get the active requests for this hive
     */
    @Override
    public void getHiveRequests() {
        server.getHiveRequests();
    }

    /**
     * Handles a successful server request for hive requests
     * @param response The server response containing all requests for this hive
     * @throws JSONException
     */
    @Override
    public void onHiveRequestSuccess(JSONArray response) throws JSONException {
        for(int i = 0; i<response.length(); i++){
            if(isActive(response.getJSONObject(i))){
                view.addToRequests(response.getJSONObject(i));
            }
        }
    }

    /**
     * Determines if the given hive request is active or not
     * @param hiveRequest The hive request to check
     * @return Returns true if active, false if inactive
     * @throws JSONException
     */
    public boolean isActive(JSONObject hiveRequest) throws JSONException {
        return hiveRequest.getBoolean("isActive");
    }

    /**
     * Handles receiving an error from the server call
     */
    @Override
    public void onError() {
        view.clearData();
        getHiveRequests();
    }

    /**
     * Gets and returns the context from the IHiveRequestView
     * @return The context of the IHiveRequestView associated with this HiveRequestLogic
     */
    @Override
    public Context getRequestsContext() {
        return view.getRequestsContext();
    }

    /**
     * Calls the server class to accept or deny the request
     * @param request The request to accept or deny
     * @param status The status of this request, accepted or denied
     * @throws JSONException
     */
    @Override
    public void handleRequestLogic(JSONObject request, String status) throws JSONException {
        server.handleRequest(request, status);
    }

    /**
     * Handles a successful call to accept or deny a request
     */
    @Override
    public void onAcceptDenySuccess() {
        view.clearData();
        getHiveRequests();
    }
}
