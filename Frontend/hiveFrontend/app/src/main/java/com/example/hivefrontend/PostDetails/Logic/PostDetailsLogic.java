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

public class PostDetailsLogic implements IPostVolleyListener{

    IPostView p;
    IPostServerRequest server;
    public PostDetailsLogic(IPostView p, IPostServerRequest server){
        this.p = p;
        this.server=server;
        server.addVolleyListener(this);
    }

    public Context getPostContext(){
        return p.getPostContext();
    }

    public int getPostId(){
        return p.getPostId();
    }

    public void getPostInfoJson(int postId){
        server.requestPostJson(postId);
    }




    public void checkLikesAndPost(){
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
            p.setHiveName(response.getString("name"));
            setPostInfo(post);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setPostInfo(JSONObject post) throws JSONException {

        p.setPost(post);

    }

    public void onCommentPostSuccess() {
        p.handleCommentSuccess();
        getPostInfoJson(p.getPostId());
    }
}
