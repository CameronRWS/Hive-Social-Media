package com.example.hivefrontend.PostDetails.Logic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hivefrontend.PostDetails.IPostView;
import com.example.hivefrontend.PostDetails.Network.IPostServerRequest;
import com.example.hivefrontend.PostDetails.Network.ServerRequest;
import com.example.hivefrontend.Profile.ProfileActivity;
import com.example.hivefrontend.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Logic for the PostDetailsActivity
 */
public class PostDetailsLogic implements IPostVolleyListener{

    IPostView p;
    IPostServerRequest server;

    /**
     * Creates a PostDetailsLogic with the given IPostView and IPostServerRequest
     * @param p The IPostView for this PostDetailsLogic
     * @param server The IPostServerRequest for this PostDetailsLogic
     */
    public PostDetailsLogic(IPostView p, IPostServerRequest server){
        this.p = p;
        this.server=server;
        server.addVolleyListener(this);
    }

    /**
     * Gets the user id of the current user
     * @return The user id of the current user
     */
    public int getUserId(){
        return p.getUserId();
    }

    /**
     * Returns the Context of the IPostView of this PostDetailsLogic
     * @return Context of the IPostView
     */
    public Context getPostContext(){
        return p.getPostContext();
    }

    /**
     * Returns the post id of the post being displayed
     * @return The post id of the post displayed
     */
    public int getPostId(){
        return p.getPostId();
    }

    /**
     * Calls the server class to get the post information for the given post id
     * @param postId The post id of the post to display
     */
    public void getPostInfoJson(int postId){
        server.requestPostJson(postId);
    }

    /**
     * Calls a server class to like this post
     */
    public void checkLikesAndPost(){
        server.checkLikesAndPost();
    }

    /**
     * Handles user click on the username of display name of this post
     * @param userId User id of the user to display
     * @param view The provided View
     */
    public void onUserClick(int userId, View view) {
        Intent intent = new Intent(view.getContext(), ProfileActivity.class);
        //start new activity and pass the user ID to it
        intent.putExtra("userId", userId);
        view.getContext().startActivity(intent);
    }

    /**
     * Handles a VolleyError from a server call
     * @param error The VolleyError received
     */
    public void onError(VolleyError error){
        Log.i("volleyAppError","Error: " + error.getMessage());
        Log.i("volleyAppError","VolleyError: "+ error);
    }

    /**
     * Handles a successful request to get post information
     * @param response The response from the server from the hive name request
     * @param post The response from the server from the post details request
     */
    public void onPostRequestSuccess(JSONObject response, JSONObject post) {
        try {
            p.setHiveName(response.getString("name"));
            setPostInfo(post);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Calls the IPostView to display the received post's information
     * @param post The post to display
     * @throws JSONException
     */
    private void setPostInfo(JSONObject post) throws JSONException {
        p.setPost(post);
    }

    /**
     * Handles a successful comment
     */
    public void onCommentPostSuccess() {
        p.handleCommentSuccess();
        getPostInfoJson(p.getPostId());
    }
}
