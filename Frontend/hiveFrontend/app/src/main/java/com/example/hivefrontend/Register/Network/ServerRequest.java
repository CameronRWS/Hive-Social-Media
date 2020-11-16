package com.example.hivefrontend.Register.Network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hivefrontend.Register.Logic.IRegisterVolleyListener;
import com.example.hivefrontend.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The server request for registering a new user.
 */
public class ServerRequest implements IRegisterServerRequest {

    private String tag_json_obj = "json_obj_req";
    private IRegisterVolleyListener registerVolleyListener;

    /**
     * Adds the given register logic to this instance
     * @param r The register logic
     */
    @Override
    public void addVolleyListener(IRegisterVolleyListener r) {
        this.registerVolleyListener = r;
    }

    /**
     * Checks to see if the username or email address is available prior to registering for it.
     */
    @Override
    public void availableCheck() {
        // check to see if the username/email address is available.
        // if it is, call registerUser
        // if not, send a message and focus request to the main activity
        String url ="http://10.24.227.37:8080/userRegistrations";
        JsonArrayRequest arrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (registerVolleyListener.isAvailable(response)) {
                                registerUser();
                            }
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
        VolleySingleton.getInstance(registerVolleyListener.getRegisterContext()).addToRequestQueue(arrayRequest);

    }

    /**
     * Invokes the logic to register a new user.
     */
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
