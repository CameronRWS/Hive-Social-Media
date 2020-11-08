package com.example.hivefrontend.PostDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.hivefrontend.GlideApp;
import com.example.hivefrontend.PostDetails.Logic.PostDetailsLogic;
import com.example.hivefrontend.PostDetails.Network.ServerRequest;
import com.example.hivefrontend.Profile.ProfileActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.SharedPrefManager;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Displays a detailed view of a post and allows the user to see and add comments.
 */
public class PostDetailsActivity extends AppCompatActivity implements IPostView{


    public String hiveName;
    public ArrayList<JSONObject> comments;
    public ArrayList<JSONObject> likes;
    public PostCommentAdapter commentAdapter;
    public int postId;
    public PostDetailsLogic logic;
    private int currentUserId;
    private ImageView postImage;
    private ImageView header;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ServerRequest server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        currentUserId = SharedPrefManager.getInstance(this.getApplicationContext()).getUser().getId();
        postImage = findViewById(R.id.photoEnlarged);
        header = findViewById(R.id.header);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        //intent should have grabbed post id
        postId = getIntent().getIntExtra("postId",0);
        comments = new ArrayList<>();
        server = new ServerRequest();
        setLogic(new PostDetailsLogic(this,server));
        server.addVolleyListener(logic);
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
                promptDialog();
            }
        });

        getPostInfo(postId);
        
        StorageReference test1 = storageReference.child("posts/" + postId + ".jpg");
        GlideApp.with(this)
                .load(test1)
                .into(postImage);
    }

    /**
     * Returns the current user's user id
     * @return The current user's user id
     */
    public int getUserId(){
        return currentUserId;
    }

    /**
     * Builds an alertDialog that allows the user to submit comments on the post
     */
    public void promptDialog() {
        LayoutInflater li = LayoutInflater.from(PostDetailsActivity.this);
        View promptsView = li.inflate(R.layout.prompts, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                PostDetailsActivity.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                if(userInput.getText().toString().length()==0){
                                    Toast.makeText(PostDetailsActivity.this,"Cannot submit an empty comment!", Toast.LENGTH_LONG);
                                }
                                else {
                                    server.postComment(userInput.getText().toString());
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /**
     * Calls the logic class to request the post information for the given post id
     * @param postId The post id of the post to display
     */
    public void getPostInfo(int postId){
        logic.getPostInfoJson(postId);
    }

    /**
     * Sets the PostDetailsLogic variable to the provided one
     * @param logic The logic class to use for this PostDetailsActivity
     */
    public void setLogic(PostDetailsLogic logic){
        if(this.logic==null) this.logic = logic;
    }

    /**
     * Handles user click on the username or display name of the poster by starting the ProfileActivity
     * @param userId The userId of the user
     * @param view The provided view
     */
    public void onUserClick(int userId, View view){
        logic.onUserClick(userId, view);

        Intent intent = new Intent(view.getContext(), ProfileActivity.class);

            //start new activity and pass the user ID to it
            intent.putExtra("userId", userId);
            view.getContext().startActivity(intent);
    }

    /**
     * Returns the Context for this activity
     * @return Context for this activity
     */
    @Override
    public Context getPostContext() {
        return PostDetailsActivity.this;
    }

    /**
     * Returns the post id for the post currently displayed
     * @return The post id for the displayed post
     */
    public int getPostId() {
        return postId;
    }

    /**
     * Sets the hive name of the post to the given name
     * @param name The hive name to set this post's hive name to
     */
    @Override
    public void setHiveName(String name) {
        hiveName = name;
    }

    /**
     * Using the given post, sets the TextViews of the screen to display details of the post
     * @param post The post to display
     * @throws JSONException
     */
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

    /**
     * Handles a successful comment
     */
    @Override
    public void handleCommentSuccess() {
        comments.clear();
        commentAdapter.notifyDataSetChanged();
    }
}