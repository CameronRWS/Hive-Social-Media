package com.example.hivefrontend.ui.hive.Network;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.hive.Logic.IHiveVolleyListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerRequest implements  IHiveServerRequest{
    private static com.example.hivefrontend.ui.hive.Logic.IHiveVolleyListener logic;

    /**
     * Assigns this instance's logic to the parameter
     * @param l The parameter, or, the given logic
     */
    @Override
    public void addVolleyListener(IHiveVolleyListener l) { logic = l;}

    /**
     * Invokes the logic to handle displaying the hive page
     * @param hiveName The string literal which holds the display name of the hive.
     */
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

    /**
     * Invokes another method of this instance and the logic to handle a page resume
     */
    public void pageResumeRequests(){
        updatePostRequest();
        logic.notifyDataSetChanged();
    }

    /**
     * Invokes another method of this instance and the logic to handle the instance of updating a post
     */
    public void updatePostRequest() {
        setUserHiveRequest(logic.getUserId());
        logic.clearAdapterData();
        logic.notifyDataSetChanged();
        getHomePosts();
    }


    public static void getHomePosts() {
        for (int i = 0; i < logic.getHiveIdsHome().size(); i++) {
            int hiveId = logic.getHiveIdsHome().get(i);

            String url = "http://10.24.227.37:8080/posts/byHiveId/" + hiveId;
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
            VolleySingleton.getInstance(logic.getHiveContext()).addToRequestQueue(hivePostRequest);
        }
    }

    public static void sortPosts(){
        logic.sortData();
        logic.notifyDataSetChanged();
    }

    /**
     * Parses the server data to find the number of likes a post has by id.
     * @param postId A given post's id
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

    /**
     * Handles liking a post
     * @param postId The given post's id
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

        VolleySingleton.getInstance(logic.getHiveContext()).addToRequestQueue(jsonObjectRequest);
    }


    /**
     * Handles the action of setting up a hive page by invoking the logic
     * @param userId The current session's user's id.
     */
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

    /**
     * Invokes the logic to fetch the number of members is in the hive to display.
     * @param hiveName The string literal which holds this hive page's display name.
     */
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
