package com.example.hivefrontend.EditProfile.Network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hivefrontend.EditProfile.Logic.IEPVolleyListener;
import com.example.hivefrontend.Login.Logic.ILoginVolleyListener;
import com.example.hivefrontend.VolleySingleton;

import org.json.JSONArray;

public class ServerRequest implements IEPServerRequest {

    private String tag_json_obj = "json_obj_req";
    private IEPVolleyListener epVolleyListener;

    @Override
    public void addVolleyListener(IEPVolleyListener l) {
        this.epVolleyListener = l;
    }

    @Override
    public void onSave() {
        Log.i("bigmacburger", "entered onsave");
        String url ="http://10.24.227.37:8080/users";
        Log.i("bigmacburger", "got the string url");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("bigmacburger", "on response");
                        //epVolleyListener.onSaveSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        VolleySingleton.getInstance(epVolleyListener.getEPContext()).addToRequestQueue(jsonArrayRequest);
    }
}
