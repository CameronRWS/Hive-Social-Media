package com.example.hivefrontend.Hive.Network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hivefrontend.Hive.Logic.IHiveVolleyListener;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.profile.ProfileVolleyListener;

import org.json.JSONArray;

public class ServerRequest implements IHiveServerRequest {

    private String tag_json_obj = "json_obj_req";

    private IHiveVolleyListener hiveVolleyListener;
    @Override
    public void addVolleyListener(IHiveVolleyListener logic) { this.hiveVolleyListener = logic;}

    @Override
    public String getUserId(final String email) {
        final String[] userId = {""};
        String url ="http://10.24.227.37:8080/userRegistrations";
        JsonArrayRequest arrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        userId[0] = hiveVolleyListener.onGetUserIdSuccess(response, email);
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);
                    }
                });
        VolleySingleton.getInstance(hiveVolleyListener.getHiveContext()).addToRequestQueue(arrayRequest);
        return userId[0];
    }

    @Override
    public void displayScreen(int userId) {
        String url = "http://10.24.227.37:8080/members/byUserId/" + userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        hiveVolleyListener.onGetHiveSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);
                    }
                });
        VolleySingleton.getInstance(hiveVolleyListener.getHiveContext()).addToRequestQueue(jsonArrayRequest);
    }
}
