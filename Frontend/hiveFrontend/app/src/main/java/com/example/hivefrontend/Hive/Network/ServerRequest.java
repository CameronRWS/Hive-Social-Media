package com.example.hivefrontend.Hive.Network;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hivefrontend.Hive.Logic.IHiveVolleyListener;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.profile.ProfileVolleyListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerRequest implements IHiveServerRequest {

    private String tag_json_obj = "json_obj_req";

    private static IHiveVolleyListener logic;
    @Override
    public void addVolleyListener(IHiveVolleyListener logic) { this.logic = logic;}

    @Override
    public void displayScreen(final String hiveName) {
        String url = "http://10.24.227.37:8080/hives";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        logic.onGetHiveNameSuccess(response, hiveName);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);
                    }
                });
        VolleySingleton.getInstance(logic.getHiveContext()).addToRequestQueue(jsonArrayRequest);
    }

    public void pageResumeRequests(){
        updatePostRequest();
        logic.notifyDataSetChanged();
    }

    public void updatePostRequest() {
        setUserHiveRequest(logic.getUserId());
        logic.clearAdapterData();
        logic.notifyDataSetChanged();
    }
    public void checkLikes(final int postId){
        String url ="http://10.24.227.37:8080/likes/byPostId/" + postId;
        JsonArrayRequest likeRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean liked = false;
                            for(int i= 0; i<response.length();i++){
                                JSONObject likeObject = response.getJSONObject(0);
                                if(likeObject.getJSONObject("user").getInt("userId")==logic.getUserId()){
                                    liked = true;
                                }
                            }
                            if(!liked) {
                                postLike(postId);
                            }
                            else{
                                Toast.makeText(logic.getHiveContext(), "You've already liked this buzz!", Toast.LENGTH_LONG).show();
                            }
                        }
                        catch (JSONException e) {
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
        VolleySingleton.getInstance(logic.getHiveContext()).addToRequestQueue(likeRequest);
        updatePostRequest();
    }
    public void postLike(int postId){
        String url ="http://10.24.227.37:8080/likes";
        Log.i("post id in home frag", " " + postId);

        final JSONObject postObject = new JSONObject();
        try{
            postObject.put("postId",postId);
            postObject.put("userId",logic.getUserId());
            //Toast.makeText(getApplicationContext(), "Buzz liked successfully!", Toast.LENGTH_LONG).show();

        } catch (JSONException e){
            e.printStackTrace();
            //Toast.makeText(this, "Error liking this post. Try again.", Toast.LENGTH_LONG).show();
        }
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                postObject, new Response.Listener<JSONObject>(){

            public void onResponse(JSONObject response) {
                Log.i("request","success!");
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error){
                Log.i("request","fail!");
            }
        });
        // Add the request to the RequestQueue.

        VolleySingleton.getInstance(logic.getHiveContext()).addToRequestQueue(jsonObjectRequest);
    }


    public void setUserHiveRequest(int userId) {
        String url = "http://10.24.227.37:8080/members/byUserId/" + userId;
        JsonArrayRequest hiveRequest = new JsonArrayRequest (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                logic.onHiveRequestSuccess(response);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                logic.onError(error);

            }
        });
        VolleySingleton.getInstance(logic.getHiveContext()).addToRequestQueue(hiveRequest);
    }

    @Override
    public void fetchMemberCount(final String hiveName) {
        String url = "http://10.24.227.37:8080/members";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        logic.onFetchMemberCountSuccess(response, hiveName);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);
                    }
                });
        VolleySingleton.getInstance(logic.getHiveContext()).addToRequestQueue(jsonArrayRequest);
    }


}
