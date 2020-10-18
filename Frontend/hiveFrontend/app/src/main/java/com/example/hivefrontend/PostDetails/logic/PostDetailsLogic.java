package com.example.hivefrontend.PostDetails.Logic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hivefrontend.PostDetails.Network.ServerRequest;
import com.example.hivefrontend.PostDetails.PostDetailsActivity;
import com.example.hivefrontend.ProfileActivity;
import com.example.hivefrontend.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PostDetailsLogic {

    PostDetailsActivity p;
    public PostDetailsLogic(PostDetailsActivity p){
        this.p = p;
    }

    public Context getPostContext(){
        return p.getApplicationContext();
    }

    public int getPostId(){
        return p.getPostId();
    }
    public void getPostInfoJson(int postId){
        ServerRequest server = new ServerRequest(this);
        server.requestPostJson(postId);
    }


    public void promptDialog() {
        LayoutInflater li = LayoutInflater.from(p);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                p);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        final PostDetailsLogic l = this;
        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                if(userInput.getText().toString().length()==0){
                                    Toast.makeText(p.getApplicationContext(),"Cannot submit an empty comment!", Toast.LENGTH_LONG);
                                }
                                else {
                                    ServerRequest server = new ServerRequest(l);
                                    server.postComment(userInput.getText().toString());
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

    public void checkLikesAndPost(){
        ServerRequest server = new ServerRequest(this);
        server.checkLikesAndPost();
    }

    public void onUserClick(int userId, View view) {
        Intent intent = new Intent(view.getContext(), ProfileActivity.class);

        //start new activity and pass the user ID to it
        intent.putExtra("userId", userId);
        view.getContext().startActivity(intent);
    }
    public void onError(VolleyError error){
        Log.i("volleyAppError","Error: " + error.getMessage());
        Log.i("volleyAppError","VolleyError: "+ error);
    }

    public void onPostRequestSuccess(JSONObject response, JSONObject post) {
        try {
            this.p.hiveName = response.getString("name");
            setPostInfo(post);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setPostInfo(JSONObject post) throws JSONException {
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

    public void onCommentPostSuccess() {
        p.comments.clear();
        p.commentAdapter.notifyDataSetChanged();
        getPostInfoJson(p.postId);
        Log.i("request","success!");
    }
}
