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

/**
 * Interface implemented by the ServerRequest for the home screen
 */
public interface IServerRequest {
    void addVolleyListener(IHomeVolleyListener l);

    void setUserHiveRequest(int userId);

    void pageResumeRequests();

    void updatePostRequest();

    void getDiscoverPosts();

    void checkLikes(final int postId);

    void postLike(int postId);
}
