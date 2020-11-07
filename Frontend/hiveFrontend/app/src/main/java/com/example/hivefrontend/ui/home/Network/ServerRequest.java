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

/**
 * Class to make server requests needed by the home screen
 */
public class ServerRequest implements IServerRequest{

    private static IHomeVolleyListener logic;

    /**
     * Adds a IHomeVolleyListener to this ServerRequest instance
     * @param l The IHomeVolleyListener to add
     */
    public void addVolleyListener(IHomeVolleyListener l){
        logic = l;
    }

    /**
     * Gets the hives that the currently logged in user is a part of
     * @param userId The user id of the current user
     */
    public void setUserHiveRequest(int userId){
        //Request: hive information of this user
        String url ="http://10.24.227.37:8080/members/byUserId/" + userId;

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

    /**
     * Method called when the page resumes
     */
    public void pageResumeRequests(){
        updatePostRequest();
        logic.notifyDataSetChanged();
    }

    /**
     * Method called when the post information needs updating
     */
    public void updatePostRequest() {
        setUserHiveRequest(logic.getUserId());
        logic.clearAdapterData();
        logic.notifyDataSetChanged();
        getDiscoverPosts();
    }

    /**
     * Gets the posts from the server to display on the discover page.
     * This method causes a chain of subsequent requests to get the discover page hive's names and the home posts.
     */
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


    /**
     * Calls the logic methods to sort posts and notify adapters of a data change.
     */
    public static void sortPosts(){
        logic.sortData();
        logic.notifyDataSetChanged();
    }

    /**
     * Makes a server call to get the hive names for the hives shown on the discover page
     */
    public static void getDiscoverHives(){
        //get each hive
        for(int i = 0; i<logic.getHiveIdsDiscover().size(); i++){
            int hiveId = logic.getHiveIdsDiscover().get(i);

            String url ="http://10.24.227.37:8080/hives/byHiveId/" + hiveId;
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

    /**
     * Makes a server call to get the posts to display on the home page using the current user's hive ids.
     */
    public static void getHomePosts(){
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

    /**
     * Checks if the logged in user has liked the current post. If not, calls postLike to like the post.
     * @param postId The post id of the post to like
     */
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

    /**
     * Makes a server call to like the post
     * @param postId Post id of the post to be liked
     */
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

        VolleySingleton.getInstance(logic.getHomeContext()).addToRequestQueue(jsonObjectRequest);
    }
}
