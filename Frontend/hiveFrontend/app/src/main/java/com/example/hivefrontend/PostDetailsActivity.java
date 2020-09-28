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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        //intent should have grabbed post id
        int postId = getIntent().getIntExtra("postId",0);
        TextView titleTextView = findViewById(R.id.postTitle);
        titleTextView.setText(""+postId);
        queue = Volley.newRequestQueue(this);

        String url ="http://10.24.227.37:8080/posts/byPostId/" + postId; //for now, getting this user's hive information until we have login functionality
        JsonObjectRequest postDetailsRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            setPostInfo(response);
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

    public void setPostInfo(JSONObject post) throws JSONException {
        TextView titleText = this.findViewById(R.id.postTitle);
        String title = post.getString("title");
        titleText.setText(title);
    }
}