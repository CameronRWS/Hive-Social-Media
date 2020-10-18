package com.example.hivefrontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hivefrontend.ui.home.HomeAdapter;
import com.example.hivefrontend.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostDetailsActivity extends AppCompatActivity {


    private RequestQueue queue;
    private String hiveName;
    private ArrayList<JSONObject> comments;
    private ArrayList<JSONObject> likes;
    private PostCommentAdapter commentAdapter;
    private int postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        //intent should have grabbed post id
        postId = getIntent().getIntExtra("postId",0);
        queue = Volley.newRequestQueue(this);
        comments = new ArrayList<>();

        final RecyclerView recyclerView = findViewById(R.id.postViewRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        commentAdapter = new PostCommentAdapter(getApplicationContext(), comments);
        recyclerView.setAdapter(commentAdapter);

        ImageView likeButton = findViewById(R.id.likeCountIcon);
        likeButton.setClickable(true);
        likeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                checkLikesAndPost();
            }
        });

        ImageView commentButton = findViewById(R.id.commentIcon);
        commentButton.setClickable(true);
        commentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //prompts a dialog box, will post the comment
                promptDialog();
            }
        });

        getPostJson();
    }

    private void promptDialog(){

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);


        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                if(userInput.getText().toString().length()==0){
                                    Toast.makeText(getApplicationContext(),"Cannot submit an empty comment!", Toast.LENGTH_LONG);
                                }
                                else {
                                    postComment(userInput.getText().toString());
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void getPostJson(){
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


    //post comment
    private void postComment(String comment){

        Log.i(" status ", "got into post comment ");

        String url ="http://10.24.227.37:8080/comments";


        final JSONObject postObject = new JSONObject();
        try{
            postObject.put("postId",postId);
            postObject.put("userId",2);
            postObject.put("textContent", comment);

        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(this, "Error commenting on this post. Try again.", Toast.LENGTH_LONG).show();
        }


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                postObject, new Response.Listener<JSONObject>(){

            public void onResponse(JSONObject response) {
                comments.clear();
                commentAdapter.notifyDataSetChanged();
                getPostJson();
                Log.i("request","success!");
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error){
                Log.i("request","fail!");
            }
        });
        // Add the request to the RequestQueue.

        queue.add(jsonObjectRequest);
    }

    //checks if the user has already liked this post--if not, will add the like
    private void checkLikesAndPost(){
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
                                postLike();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "You've already liked this buzz!", Toast.LENGTH_LONG).show();
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
        queue.add(likeRequest);
    }

    private void postLike(){

        String url ="http://10.24.227.37:8080/likes";


        final JSONObject postObject = new JSONObject();
        try{
            postObject.put("postId",postId);
            postObject.put("userId",2);
            Toast.makeText(getApplicationContext(), "Buzz liked successfully!", Toast.LENGTH_LONG).show();

        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(this, "Error liking this post. Try again.", Toast.LENGTH_LONG).show();
        }


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                postObject, new Response.Listener<JSONObject>(){

            public void onResponse(JSONObject response) {
                getPostJson();

                Log.i("request","success!");
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error){
                Log.i("request","fail!");
            }
        });
        // Add the request to the RequestQueue.

        queue.add(jsonObjectRequest);
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
        String likes = String.valueOf(post.getJSONArray("likes").length());
        numLikes.setText(likes);
        //number comments
        TextView numComments = this.findViewById(R.id.commentNumber);
        String comment = String.valueOf(post.getJSONArray("comments").length());
        numComments.setText(comment);

        JSONArray arrComments = post.getJSONArray("comments");
        for(int i = 0; i<arrComments.length(); i++){
            comments.add(arrComments.getJSONObject(i));
        }
        Log.i(" status ", "got to end of post info set");
        Log.i(" comments ", comments.toString());
        commentAdapter.notifyDataSetChanged();


        final int userId = post.getJSONObject("user").getInt("userId");
        displayName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onUserClick(userId, view);
            }
        });
        userNameTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onUserClick(userId, view);

            }
        });
    }

    private void onUserClick(int userId, View view){
        Intent intent = new Intent(view.getContext(), ProfileActivity.class);

            //start new activity and pass the user ID to it
            intent.putExtra("userId", userId);
            view.getContext().startActivity(intent);
    }
}