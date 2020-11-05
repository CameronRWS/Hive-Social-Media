package com.example.hivefrontend.HiveRequests.Server;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hivefrontend.HiveRequests.Logic.HiveRequestLogic;
import com.example.hivefrontend.HiveRequests.Logic.IHiveRequestVolleyListener;
import com.example.hivefrontend.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HiveRequestServerRequest implements IHiveRequestServerRequest {

    private IHiveRequestVolleyListener logic;
    @Override
    public void addVolleyListener(IHiveRequestVolleyListener logic) {
        this.logic = logic;
    }

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

    @Override
    public void acceptRequest(JSONObject request, final String status) throws JSONException {
        final int hiveId = request.getInt("hiveId");
        final int userId = request.getJSONObject("user").getInt("userId");


        final JSONObject req = new JSONObject();
        req.put("hiveId",hiveId);
        req.put("userId",userId);
        req.put("status",status);

        String url = "http://10.24.227.37:8080/requests";
        final JsonObjectRequest hiveReqAccept = new JsonObjectRequest
                (Request.Method.DELETE, url, null, new Response.Listener<JSONObject>() {
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("hiveId",String.valueOf(hiveId));
                headers.put("userId",String.valueOf(userId));
                headers.put("status",status);
                return headers;
            }
            };
        VolleySingleton.getInstance(logic.getRequestsContext()).addToRequestQueue(hiveReqAccept);

    }


}
