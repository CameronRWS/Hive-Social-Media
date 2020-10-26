package com.example.hivefrontend.PostDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.hivefrontend.GlideApp;
import com.example.hivefrontend.PostDetails.Logic.PostDetailsLogic;
import com.example.hivefrontend.PostDetails.Network.ServerRequest;
import com.example.hivefrontend.Profile.ProfileActivity;
import com.example.hivefrontend.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostDetailsActivity extends AppCompatActivity implements IPostView{


    private RequestQueue queue;
    public String hiveName;
    public ArrayList<JSONObject> comments;
    public ArrayList<JSONObject> likes;
    public PostCommentAdapter commentAdapter;
    public int postId;
    public PostDetailsLogic logic;

    private ImageView postImage;
    private ImageView header;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        postImage = findViewById(R.id.postImage);
        header = findViewById(R.id.header);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
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

        StorageReference test1 = storageReference.child("posts/" + postId + ".jpg");
        GlideApp.with(this)
                .load(test1)
                .into(postImage);
    }

    public void onUserClick(int userId, View view){
        logic.onUserClick(userId, view);

        Intent intent = new Intent(view.getContext(), ProfileActivity.class);

            //start new activity and pass the user ID to it
            intent.putExtra("userId", userId);
            view.getContext().startActivity(intent);
    }

    @Override
    public Context getPostContext() {
        return this.getApplicationContext();
    }

    public int getPostId() {
        return postId;
    }

    @Override
    public void setHiveName(String name) {
        hiveName = name;
    }

    @Override
    public void setPost(JSONObject post) throws JSONException {
        //title
        TextView titleText = findViewById(R.id.postTitle);
        String title = post.getString("title");
        titleText.setText(title);

        //content
        TextView postContent = findViewById(R.id.postContent);
        String content = post.getString("textContent");
        postContent.setText(content);

        //user display name
        TextView displayName = findViewById(R.id.userDisplayName);
        String name = post.getJSONObject("user").getString("displayName");
        displayName.setText(name);
        //user name
        TextView userNameTextView = findViewById(R.id.userName);
        String userName = post.getJSONObject("user").getString("userName");
        userNameTextView.setText(userName);
        //hive name
        TextView hive = findViewById(R.id.hiveName);
        hive.setText(hiveName);
        //number likes
        TextView numLikes = findViewById(R.id.likeNumber);
        String likes = String.valueOf(post.getJSONArray("likes").length());
        numLikes.setText(likes);
        //number comments
        TextView numComments = findViewById(R.id.commentNumber);
        String comment = String.valueOf(post.getJSONArray("comments").length());
        numComments.setText(comment);

        JSONArray arrComments = post.getJSONArray("comments");
        for(int i = 0; i<arrComments.length(); i++){
            comments.add(arrComments.getJSONObject(i));
        }
        commentAdapter.notifyDataSetChanged();

        final int userId = post.getJSONObject("user").getInt("userId");
        displayName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onUserClick(userId, view);
            }
        });
        userNameTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                onUserClick(userId, view);

            }
        });
    }

    @Override
    public void handleCommentSuccess() {
        comments.clear();
        commentAdapter.notifyDataSetChanged();
    }
}