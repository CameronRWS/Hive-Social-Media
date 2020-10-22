package com.example.hivefrontend.ui.buzz.Network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.buzz.Logic.IBuzzVolleyListener;
import com.example.hivefrontend.ui.profile.ProfileVolleyListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServerRequest implements IBuzzServerRequest {

    private String tag_json_obj = "json_obj_req";
    private IBuzzVolleyListener buzzVolleyListener;

    @Override
    public void addVolleyListener(IBuzzVolleyListener l) {
        buzzVolleyListener = l;
    }

    @Override
    public void getHives(int userId) {
        String url ="http://10.24.227.37:8080/members/byUserId/" + userId;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        buzzVolleyListener.onGetHivesSuccess(response);
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);
                    }
                });
        VolleySingleton.getInstance(buzzVolleyListener.getBuzzContext()).addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public void makeBuzz() {
        String url ="http://10.24.227.37:8080/posts";
        JSONObject buzz = buzzVolleyListener.createBuzzPost();
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                buzz, new Response.Listener<JSONObject>(){
            public void onResponse(JSONObject response) {
                Log.i("request","success!");
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error){
                Log.i("request","fail!");
            }
        });
        VolleySingleton.getInstance(buzzVolleyListener.getBuzzContext()).addToRequestQueue(jsonObjectRequest);
    }

}
