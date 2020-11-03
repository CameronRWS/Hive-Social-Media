package com.example.hivefrontend.Register.Network;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hivefrontend.EditProfileActivity;
import com.example.hivefrontend.Register.Logic.IRegisterVolleyListener;
import com.example.hivefrontend.Register.RegisterActivity;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.User;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.buzz.Logic.IBuzzVolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ServerRequest implements IRegisterServerRequest {

    private String tag_json_obj = "json_obj_req";
    private IRegisterVolleyListener registerVolleyListener;

    @Override
    public void addVolleyListener(IRegisterVolleyListener r) {
        this.registerVolleyListener = r;
    }

    @Override
    public void registerUser() {
        String url = "http://10.24.227.37:8080/userRegistrations";
        JSONObject obj = registerVolleyListener.createUser();
        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest
                (Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            registerVolleyListener.onRegisterUserSuccess(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("request","fail!");
                            }
                        });

        VolleySingleton.getInstance(registerVolleyListener.getRegisterContext()).addToRequestQueue(jsonArrayRequest);
    }
}
