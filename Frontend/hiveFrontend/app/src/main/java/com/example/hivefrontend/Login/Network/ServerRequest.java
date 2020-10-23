package com.example.hivefrontend.Login.Network;

import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hivefrontend.Login.Logic.ILoginVolleyListener;
import com.example.hivefrontend.MainActivity;
import com.example.hivefrontend.Register.Logic.IRegisterVolleyListener;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.User;
import com.example.hivefrontend.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerRequest implements ILoginServerRequest {

    private String tag_json_obj = "json_obj_req";
    private ILoginVolleyListener loginVolleyListener;

    @Override
    public void loginUser() {
        String url ="http://10.24.227.37:8080/userRegistrations";
        JsonArrayRequest arrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            loginVolleyListener.login(response);
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

                    }
                });
        VolleySingleton.getInstance(loginVolleyListener.getLoginContext()).addToRequestQueue(arrayRequest);
    }
}
