package com.example.hivefrontend.ui.profile.Network;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.profile.IProfileServerRequest;
import com.example.hivefrontend.ui.profile.Logic.ProfileLogic;
import com.example.hivefrontend.ui.profile.ProfileFragment;
import com.example.hivefrontend.ui.profile.ProfileVolleyListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerRequest implements IProfileServerRequest {
    private String tag_json_obj = "json_obj_req";

    private ProfileVolleyListener profileVolleyListener;

   public void addVolleyListener(ProfileVolleyListener logic){
       profileVolleyListener = logic;
   }

    public void userInfoRequest(){

        String url ="http://10.24.227.37:8080/users";

        // Server name http://coms-309-tc-03.cs.iastate.edu:8080/posts

        //first request: user information
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        profileVolleyListener.onUserInfoSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
        VolleySingleton.getInstance(profileVolleyListener.getProfileContext()).addToRequestQueue(jsonArrayRequest);
    }


    public void hiveListRequest(){
        //second request: hive information
        String url ="http://10.24.227.37:8080/members/byUserId/" + profileVolleyListener.getUserId(); //for now, getting this user's hive information until we have login functionality

        JsonArrayRequest hiveRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        profileVolleyListener.onHiveListSuccess(response);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        profileVolleyListener.onHiveListError(error);
                    }
                });
        VolleySingleton.getInstance(profileVolleyListener.getProfileContext()).addToRequestQueue(hiveRequest);
    }

    public void pollenCountRequest(){
        //third request: pollen count
        String url ="http://10.24.227.37:8080/likeCount/byUserId/" + profileVolleyListener.getUserId(); //for now, getting this user's hive information until we have login functionality

        StringRequest pollenCountRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        profileVolleyListener.pollenCountSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        profileVolleyListener.pollenCountError(error);
                    }
                });
        VolleySingleton.getInstance(profileVolleyListener.getProfileContext()).addToRequestQueue(pollenCountRequest);
    }


}
