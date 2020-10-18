package com.example.hivefrontend.PostDetails.Network;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hivefrontend.PostDetails.PostDetailsActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerRequest {
    private PostDetailsActivity p;

    public ServerRequest(PostDetailsActivity p){
        this.p = p;
    }

    public void requestPostJson(int postId){
        String url ="http://10.24.227.37:8080/posts/byPostId/" + postId;
        JsonObjectRequest postDetailsRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            getHiveName(response);
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

        VolleySingleton.getInstance(p.getApplicationContext()).addToRequestQueue(postDetailsRequest);
    }

    private void getHiveName(final JSONObject post) throws JSONException {
        int hiveId = post.getInt("hiveId");

        String url ="http://10.24.227.37:8080/hives/byHiveId/" + hiveId;
        JsonObjectRequest hiveNameRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            p.hiveName = response.getString("name");
                            setPostInfo(post);
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

        VolleySingleton.getInstance(p.getApplicationContext()).addToRequestQueue(hiveNameRequest);

    }

    public void setPostInfo(JSONObject post) throws JSONException {
        //title
        TextView titleText = p.findViewById(R.id.postTitle);
        String title = post.getString("title");
        titleText.setText(title);
        //content
        TextView postContent = p.findViewById(R.id.postContent);
        String content = post.getString("textContent");
        postContent.setText(content);
        //user display name
        TextView displayName = p.findViewById(R.id.userDisplayName);
        String name = post.getJSONObject("user").getString("displayName");
        displayName.setText(name);
        //user name
        TextView userNameTextView = p.findViewById(R.id.userName);
        String userName = post.getJSONObject("user").getString("userName");
        userNameTextView.setText(userName);
        //hive name
        TextView hive = p.findViewById(R.id.hiveName);
        hive.setText(p.hiveName);
        //number likes
        TextView numLikes = p.findViewById(R.id.likeNumber);
        String likes = String.valueOf(post.getJSONArray("likes").length());
        numLikes.setText(likes);
        //number comments
        TextView numComments = p.findViewById(R.id.commentNumber);
        String comment = String.valueOf(post.getJSONArray("comments").length());
        numComments.setText(comment);

        JSONArray arrComments = post.getJSONArray("comments");
        for(int i = 0; i<arrComments.length(); i++){
            p.comments.add(arrComments.getJSONObject(i));
        }
        Log.i(" status ", "got to end of post info set");
        Log.i(" comments ", p.comments.toString());
        p.commentAdapter.notifyDataSetChanged();


        final int userId = post.getJSONObject("user").getInt("userId");
        displayName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                p.onUserClick(userId, view);
            }
        });
        userNameTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                p.onUserClick(userId, view);

            }
        });
    }

    //post comment
    public void postComment(String comment){

        Log.i(" status ", "got into post comment ");

        String url ="http://10.24.227.37:8080/comments";


        final JSONObject postObject = new JSONObject();
        try{
            postObject.put("postId",p.postId);
            postObject.put("userId",2);
            postObject.put("textContent", comment);

        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(p, "Error commenting on this post. Try again.", Toast.LENGTH_LONG).show();
        }


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                postObject, new Response.Listener<JSONObject>(){

            public void onResponse(JSONObject response) {
                p.comments.clear();
                p.commentAdapter.notifyDataSetChanged();
                requestPostJson(p.postId);
                Log.i("request","success!");
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error){
                Log.i("request","fail!");
            }
        });
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(p.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }


    public void checkLikesAndPost(){
        String url ="http://10.24.227.37:8080/likes/byPostId/" + p.postId;
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
                                postLike();
                            }
                            else{
                                Toast.makeText(p.getApplicationContext(), "You've already liked this buzz!", Toast.LENGTH_LONG).show();
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
        VolleySingleton.getInstance(p.getApplicationContext()).addToRequestQueue(likeRequest);
    }

    private void postLike(){

        String url ="http://10.24.227.37:8080/likes";


        final JSONObject postObject = new JSONObject();
        try{
            postObject.put("postId",p.postId);
            postObject.put("userId",2);
            Toast.makeText(p.getApplicationContext(), "Buzz liked successfully!", Toast.LENGTH_LONG).show();

        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(p, "Error liking this post. Try again.", Toast.LENGTH_LONG).show();
        }


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                postObject, new Response.Listener<JSONObject>(){

            public void onResponse(JSONObject response) {
                requestPostJson(p.postId);

                Log.i("request","success!");
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error){
                Log.i("request","fail!");
            }
        });
        // Add the request to the RequestQueue.

        VolleySingleton.getInstance(p.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }



}
