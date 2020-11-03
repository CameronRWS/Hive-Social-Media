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
import com.example.hivefrontend.PostDetails.Logic.IPostVolleyListener;
import com.example.hivefrontend.PostDetails.Logic.PostDetailsLogic;
import com.example.hivefrontend.PostDetails.PostDetailsActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerRequest implements IPostServerRequest{
    private IPostVolleyListener logic;

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
                        logic.onError(error);

                    }
                });

        VolleySingleton.getInstance(logic.getPostContext()).addToRequestQueue(postDetailsRequest);
    }

    public void getHiveName(final JSONObject post) throws JSONException {
        int hiveId = post.getInt("hiveId");

        String url ="http://10.24.227.37:8080/hives/byHiveId/" + hiveId;
        JsonObjectRequest hiveNameRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        logic.onPostRequestSuccess(response,post);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        logic.onError(error);
                    }
                });

        VolleySingleton.getInstance(logic.getPostContext()).addToRequestQueue(hiveNameRequest);

    }

    //post comment
    public void postComment(String comment){

        Log.i(" status ", "got into post comment ");

        String url ="http://10.24.227.37:8080/comments";


        final JSONObject postObject = new JSONObject();
        try{
            postObject.put("postId",logic.getPostId());
            postObject.put("userId",logic.getUserId());
            postObject.put("textContent", comment);

        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(logic.getPostContext(), "Error commenting on this post. Try again.", Toast.LENGTH_LONG).show();
        }


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                postObject, new Response.Listener<JSONObject>(){

            public void onResponse(JSONObject response) {
                logic.onCommentPostSuccess();
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error){
                Log.i("request","fail!");
            }
        });
        // Add the request to the RequestQueue.
        VolleySingleton.getInstance(logic.getPostContext()).addToRequestQueue(jsonObjectRequest);
    }


    public void checkLikesAndPost(){
        String url ="http://10.24.227.37:8080/likes/byPostId/" + logic.getPostId();
        JsonArrayRequest likeRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean liked = false;
                            for(int i= 0; i<response.length();i++){
                                JSONObject likeObject = response.getJSONObject(0);
                                if(likeObject.getJSONObject("user").getInt("userId")==logic.getUserId()){
                                    liked = true;
                                }
                            }
                            if(!liked) {
                                postLike();
                            }
                            else{
                                Toast.makeText(logic.getPostContext(), "You've already liked this buzz!", Toast.LENGTH_LONG).show();
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
                        logic.onError(error);

                    }
                });
        VolleySingleton.getInstance(logic.getPostContext()).addToRequestQueue(likeRequest);
    }

    private void postLike(){

        String url ="http://10.24.227.37:8080/likes";


        final JSONObject postObject = new JSONObject();
        try{
            postObject.put("postId",logic.getPostId());
            postObject.put("userId",logic.getUserId());
            Toast.makeText(logic.getPostContext(), "Buzz liked successfully!", Toast.LENGTH_LONG).show();

        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(logic.getPostContext(), "Error liking this post. Try again.", Toast.LENGTH_LONG).show();
        }


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                postObject, new Response.Listener<JSONObject>(){

            public void onResponse(JSONObject response) {
                requestPostJson(logic.getPostId());

                Log.i("request","success!");
            }

        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error){
                Log.i("request","fail!");
            }
        });
        // Add the request to the RequestQueue.

        VolleySingleton.getInstance(logic.getPostContext()).addToRequestQueue(jsonObjectRequest);
    }


    public void addVolleyListener(IPostVolleyListener l) {
        logic = l;
    }
}
