package com.example.hivefrontend.HiveRequests.Server;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hivefrontend.HiveRequests.Logic.HiveRequestLogic;
import com.example.hivefrontend.HiveRequests.Logic.IHiveRequestVolleyListener;
import com.example.hivefrontend.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;

public class HiveRequestServerRequest implements IHiveRequestServerRequest {

    private IHiveRequestVolleyListener logic;
    @Override
    public void addVolleyListener(IHiveRequestVolleyListener logic) {
        this.logic = logic;
    }

    @Override
    public void getHiveRequests() {
        String url = "http://10.24.227.37:8080/requests/byHiveId/2";
        JsonArrayRequest hiveReqsRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            logic.onHiveRequestSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);
                        logic.onError();
                    }
                });
        VolleySingleton.getInstance(logic.getRequestsContext()).addToRequestQueue(hiveReqsRequest);
    }
}
