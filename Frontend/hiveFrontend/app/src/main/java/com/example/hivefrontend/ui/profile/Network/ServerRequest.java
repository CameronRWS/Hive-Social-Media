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

/**
 * Class to handle all server requests for the ProfileFragment and ProfileActivity
 */
public class ServerRequest implements IProfileServerRequest {
    private String tag_json_obj = "json_obj_req";
    private int memberCount;

    private ProfileVolleyListener profileVolleyListener;

    /**
     * Adds the given ProfileVolleyListener to this ServerRequest
     * @param logic The ProfileVolleyListener to use
     */
   public void addVolleyListener(ProfileVolleyListener logic){
       profileVolleyListener = logic;
   }

    /**
     * Returns the stored member count
     * @return The stored member count
     */
    public int getMemberCount() {return memberCount;}

    /**
     * Makes a server call to get member objects to determine hives' member count
     * @param hiveName The hive name of the hive to count the members of
     */
    @Override
    public void fetchMemberCount(final String hiveName) {
            String url = "http://10.24.227.37:8080/members";
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                           profileVolleyListener.onFetchMemberCountSuccess(response, hiveName);
                            profileVolleyListener.onFetchHiveDescriptionSuccess(response, hiveName);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.i("volleyAppError","Error: " + error.getMessage());
                            Log.i("volleyAppError","VolleyError: "+ error);
                        }
                    });
            VolleySingleton.getInstance(profileVolleyListener.getProfileContext()).addToRequestQueue(jsonArrayRequest);

    }

    /**
     * Makes a server call to get the description of the hive with the given hiveName
     * @param hiveName The name of the hive to get the description of
     */
    @Override
    public void fetchHiveDescription(final String hiveName) {
        String url = "http://10.24.227.37:8080/hives";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        profileVolleyListener.onFetchHiveDescriptionSuccess(response, hiveName);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);
                    }
                });
        VolleySingleton.getInstance(profileVolleyListener.getProfileContext()).addToRequestQueue(jsonArrayRequest);

    }

    /**
     * Makes a server call to get the user object of this user
     */
    public void userInfoRequest(){

        String url ="http://10.24.227.37:8080/users";

        // Server name http://coms-309-tc-03.cs.iastate.edu:8080/posts

        //first request: user information
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("bigmacburger", "profile onresponse");
                        profileVolleyListener.onUserInfoSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });
        VolleySingleton.getInstance(profileVolleyListener.getProfileContext()).addToRequestQueue(jsonArrayRequest);
    }


    /**
     * Makes a server call to get the list of hives this user is a part of
     */
    public void hiveListRequest(){
        //second request: hive information
        String url ="http://10.24.227.37:8080/members/byUserId/" + profileVolleyListener.getUserId();

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

    /**
     * Makes a server call to get the pollen count of this user
     */
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
