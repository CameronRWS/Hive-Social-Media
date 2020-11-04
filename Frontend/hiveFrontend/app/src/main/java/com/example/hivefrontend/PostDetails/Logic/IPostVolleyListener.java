package com.example.hivefrontend.PostDetails.Logic;

import android.content.Context;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface IPostVolleyListener {

    void onError(VolleyError error);

    Context getPostContext();

    void onPostRequestSuccess(JSONObject response, JSONObject post);

    int getPostId();

    void onCommentPostSuccess();

    int getUserId();
}
