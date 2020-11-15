package com.example.hivefrontend.PostDetails;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Interface implemented by PostDetailsActivity
 */
public interface IPostView {

    Context getPostContext();

    int getPostId();

    void setHiveName(String name);

    void setPost(JSONObject post) throws JSONException;

    void handleCommentSuccess();

    int getUserId();
}
