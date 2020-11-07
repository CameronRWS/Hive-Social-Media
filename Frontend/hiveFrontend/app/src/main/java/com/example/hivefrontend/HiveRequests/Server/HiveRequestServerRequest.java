package com.example.hivefrontend.HiveRequests.Server;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hivefrontend.HiveRequests.Logic.HiveRequestLogic;
import com.example.hivefrontend.HiveRequests.Logic.IHiveRequestVolleyListener;
import com.example.hivefrontend.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Class to handle server requests for HiveRequestsActivity
 */
public class HiveRequestServerRequest implements IHiveRequestServerRequest {

    private IHiveRequestVolleyListener logic;

    /**
     * Adds an IHiveRequestVolleyListener to this instance
     * @param logic The IHiveRequestVolleyListener to be added
     */
    @Override
    public void addVolleyListener(IHiveRequestVolleyListener logic) {
        this.logic = logic;
    }

    /**
     * Makes a server call to get the hive requests for this hive
     */
    @Override
    public void getHiveRequests() {
        String url = "http://10.24.227.37:8080/requests/byHiveId/4";
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

    /**
     * Makes a server call to accept or deny a request based on the given status
     * @param request The request to handle
     * @param status The status to give this request--accepted or denied
     * @throws JSONException
     */
    @Override
    public void handleRequest(JSONObject request, final String status) throws JSONException {
        final int hiveId = request.getInt("hiveId");
        final int userId = request.getJSONObject("user").getInt("userId");


        final JSONObject req = new JSONObject();
        req.put("hiveId", (Integer) hiveId);
        req.put("userId", (Integer) userId);
        req.put("status",status);

        HttpStack customHurlStack = new CustomHurlStack();
        RequestQueue queue = Volley.newRequestQueue(logic.getRequestsContext(), customHurlStack);

        String url = "http://10.24.227.37:8080/requests";

        final JsonObjectRequest hiveReqAccept = new JsonObjectRequest
                (Request.Method.DELETE, url, req, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        logic.onAcceptDenySuccess();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);
                        logic.onError();
                    }
                }) {

            };

        queue.add(hiveReqAccept);

    }


}
