package com.example.hivefrontend;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PostDetailsActivity extends AppCompatActivity {


    private RequestQueue queue;
    private String hiveName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        //intent should have grabbed post id
        int postId = getIntent().getIntExtra("postId",0);
        TextView titleTextView = findViewById(R.id.postTitle);
        titleTextView.setText(""+postId);
        queue = Volley.newRequestQueue(this);

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
        queue.add(postDetailsRequest);
    }

    private void getHiveName(final JSONObject post) throws JSONException {
        int hiveId = post.getInt("hiveId");

        String url ="http://10.24.227.37:8080/hives/byHiveId/" + hiveId;
        JsonObjectRequest hiveNameRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            hiveName = response.getString("name");
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
        queue.add(hiveNameRequest);


    }

    public void setPostInfo(JSONObject post) throws JSONException {
        //title
        TextView titleText = this.findViewById(R.id.postTitle);
        String title = post.getString("title");
        titleText.setText(title);
        //content
        TextView postContent = this.findViewById(R.id.postContent);
        String content = post.getString("textContent");
        postContent.setText(content);
        //user display name
        TextView displayName = this.findViewById(R.id.userDisplayName);
        String name = post.getJSONObject("user").getString("displayName");
        displayName.setText(name);
        //user name
        TextView userNameTextView = this.findViewById(R.id.userName);
        String userName = post.getJSONObject("user").getString("userName");
        userNameTextView.setText(userName);
        //hive name
        TextView hive = this.findViewById(R.id.hiveName);
        hive.setText(hiveName);
        //number likes
        TextView numLikes = this.findViewById(R.id.likeNumber);
        String likes = String.valueOf(post.getJSONArray("likes").length())+" likes";
        numLikes.setText(likes);
        //number comments
        TextView numComments = this.findViewById(R.id.commentNumber);
        String comments = String.valueOf(post.getJSONArray("comments").length())+" comments";
        numComments.setText(comments);
    }
}