package com.example.hivefrontend.PostDetails;

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
import com.example.hivefrontend.PostDetails.Logic.PostDetailsLogic;
import com.example.hivefrontend.PostDetails.Network.ServerRequest;
import com.example.hivefrontend.ProfileActivity;
import com.example.hivefrontend.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostDetailsActivity extends AppCompatActivity {


    private RequestQueue queue;
    public String hiveName;
    public ArrayList<JSONObject> comments;
    public ArrayList<JSONObject> likes;
    public PostCommentAdapter commentAdapter;
    public int postId;
    public PostDetailsLogic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        //intent should have grabbed post id
        postId = getIntent().getIntExtra("postId",0);
        queue = Volley.newRequestQueue(this);
        comments = new ArrayList<>();
        ServerRequest server = new ServerRequest();
        logic = new PostDetailsLogic(this,server);
        final RecyclerView recyclerView = findViewById(R.id.postViewRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        commentAdapter = new PostCommentAdapter(getApplicationContext(), comments);
        recyclerView.setAdapter(commentAdapter);

        ImageView likeButton = findViewById(R.id.likeCountIcon);
        likeButton.setClickable(true);
        likeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                logic.checkLikesAndPost();
            }
        });

        ImageView commentButton = findViewById(R.id.commentIcon);
        commentButton.setClickable(true);
        commentButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //prompts a dialog box, will post the comment
                logic.promptDialog();
            }
        });


        logic.getPostInfoJson(postId);

    }


    //checks if the user has already liked this post--if not, will add the like


    public void onUserClick(int userId, View view){
        logic.onUserClick(userId, view);

        Intent intent = new Intent(view.getContext(), ProfileActivity.class);

            //start new activity and pass the user ID to it
            intent.putExtra("userId", userId);
            view.getContext().startActivity(intent);
    }

    public int getPostId() {
        return postId;
    }
}