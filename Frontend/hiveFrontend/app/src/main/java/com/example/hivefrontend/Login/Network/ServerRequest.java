package com.example.hivefrontend.Login.Network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hivefrontend.Login.Logic.ILoginVolleyListener;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.buzz.Logic.IBuzzVolleyListener;

import org.json.JSONArray;

public class ServerRequest implements ILoginServerRequest {

    private String tag_json_obj = "json_obj_req";
    private ILoginVolleyListener loginVolleyListener;

    @Override
    public void addVolleyListener(ILoginVolleyListener l) {
        this.loginVolleyListener = l;
    }


    @Override
    public void loginUser() {
        String url ="http://10.24.227.37:8080/userRegistrations";
        JsonArrayRequest arrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        loginVolleyListener.onLoginUserSuccess(response);
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
