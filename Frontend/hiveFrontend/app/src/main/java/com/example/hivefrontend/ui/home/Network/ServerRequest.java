package com.example.hivefrontend.ui.home.Network;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.home.HomeFragment;
import com.example.hivefrontend.ui.home.Logic.HomeLogic;
import com.example.hivefrontend.ui.home.Logic.IHomeVolleyListener;
import com.example.hivefrontend.ui.home.PostComparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;

public class ServerRequest implements IServerRequest{

    private static IHomeVolleyListener logic;

    public void addVolleyListener(IHomeVolleyListener l){
        logic = l;
    }

    public void setUserHiveRequest(){
        //Request: hive information of this user
        String url ="http://10.24.227.37:8080/members/byUserId/2"; //for now, getting this user's hive information until we have login functionality

        JsonArrayRequest hiveRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

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

        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(logic.getHomeContext()).addToRequestQueue(hiveRequest);
    }

    public void pageResumeRequests(){
        updatePostRequest();
        logic.notifyDataSetChanged();
    }

    public void updatePostRequest() {
        logic.clearAdapterData();
        getDiscoverPosts();
    }

    public void getDiscoverPosts() {
        //request posts from all hives:
        String url = "http://10.24.227.37:8080/posts"; //for now, getting all posts
        JsonArrayRequest hivePostRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject post = response.getJSONObject(i); //a post object
                                Integer hiveId = Integer.valueOf(post.getInt("hiveId"));
                                //if the user is in this hive, should not display their posts on the discover page
                                //if they aren't add to the list
                                if (!logic.getHiveIdsHome().contains(hiveId)) {
                                    logic.addToDiscoverIds(hiveId);
                                    logic.addToDiscoverPosts(post);
                                }
                            }
                            //now get hive options for discover page for the adapter to use
                            getDiscoverHives();
                            //post info is set for discover page, now get info for home page
                            getHomePosts();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i("jsonAppError", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError", "Error: " + error.getMessage());
                        Log.i("volleyAppError", "VolleyError: " + error);

                    }
                });

        VolleySingleton.getInstance(logic.getHomeContext()).addToRequestQueue(hivePostRequest);
    }

    //sorts the discover and home page posts chronologically, notifies the adapters of the changes
    private static void sortPosts(){
        logic.sortData();
        logic.notifyDataSetChanged();
    }

    private static void getDiscoverHives(){
        //get each hive
        for(int i = 0; i<logic.getHiveIdsDiscover().size(); i++){
            int hiveId = logic.getHiveIdsDiscover().get(i);
            //request posts from each hive:
            String url ="http://10.24.227.37:8080/hives/byHiveId/" + hiveId; //for now, getting this user's hive information until we have login functionality
            JsonObjectRequest hiveNameRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                                String name = response.getString("name");
                                logic.addToDiscoverOptions(name);
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                                Log.i("jsonAppError",e.toString());
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
            VolleySingleton.getInstance(logic.getHomeContext()).addToRequestQueue(hiveNameRequest);
        }
    }

    private static void getHomePosts(){
        //get each hive id
        for(int i = 0; i<logic.getHiveIdsHome().size(); i++){
            int hiveId = logic.getHiveIdsHome().get(i);

            //request posts from each hive:
            String url ="http://10.24.227.37:8080/posts/byHiveId/" + hiveId; //for now, getting this user's hive information until we have login functionality
            JsonArrayRequest hivePostRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try{
                                for(int i = 0; i <  response.length(); i++){
                                    JSONObject post = response.getJSONObject(i); //should a post object
                                    logic.addToHomePosts(post);
                                }
                                //now have all the posts--must sort chronologically
                                sortPosts();
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                                Log.i("jsonAppError",e.toString());
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
            VolleySingleton.getInstance(logic.getHomeContext()).addToRequestQueue(hivePostRequest);
        }
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
                                if(likeObject.getJSONObject("user").getInt("userId")==2){
                                    liked = true;
                                }
                            }
                            if(!liked) {
                                postLike(postId);
                            }
                            else{
                                Toast.makeText(logic.getHomeContext(), "You've already liked this buzz!", Toast.LENGTH_LONG).show();
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
        VolleySingleton.getInstance(logic.getHomeContext()).addToRequestQueue(likeRequest);
        updatePostRequest();
    }
    public void postLike(int postId){
        String url ="http://10.24.227.37:8080/likes";

        Log.i("post id in home frag", " " + postId);

        final JSONObject postObject = new JSONObject();
        try{
            postObject.put("postId",postId);
            postObject.put("userId",2);
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

        VolleySingleton.getInstance(logic.getHomeContext()).addToRequestQueue(jsonObjectRequest);
    }
}
