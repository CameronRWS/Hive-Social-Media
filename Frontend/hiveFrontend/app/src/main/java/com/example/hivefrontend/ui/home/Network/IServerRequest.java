package com.example.hivefrontend.ui.home.Network;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.home.Logic.IHomeVolleyListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface IServerRequest {
    public void addVolleyListener(IHomeVolleyListener l);

    public void setUserHiveRequest(int userId);

    public void pageResumeRequests();

    public void updatePostRequest();

    public void getDiscoverPosts();

    public void checkLikes(final int postId);

    public void postLike(int postId);
}
