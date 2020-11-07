package com.example.hivefrontend.Profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hivefrontend.R;
import com.example.hivefrontend.ui.profile.IProfileView;
import com.example.hivefrontend.ui.profile.MyAdapter;
import com.example.hivefrontend.ui.profile.Network.ServerRequest;
import com.example.hivefrontend.ui.profile.ProfileViewModel;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.hivefrontend.GlideApp;

import java.util.ArrayList;

/**
 * Activity to display another user's profile
 */
public class ProfileActivity extends AppCompatActivity implements IProfileView {

    private ProfileViewModel mViewModel;
    public ArrayList<Integer> hiveIds;
    public ArrayList<String> hiveOptions;
    public int userId;
    private String pollen;
    public TextView displayName;
    public ImageView locationPin;
    public TextView userName;
    public TextView pollenCount;
    public TextView displayLocation;
    public TextView hiveListHeading;
    public TextView bio;
    public TextView dateJoined;
    public RecyclerView recyclerView;
    public MyAdapter myAdapter;

    private ImageView profilePic;
    private ImageView header;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    /**
     * Upon creation, instantiates variables and makes appropriate calls to display user information
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userId = getIntent().getIntExtra("userId", -1);

        profilePic = findViewById(R.id.profilePicture);
        header = findViewById(R.id.header);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        //final ProfileLogic logic = new ProfileLogic(this);
        hiveIds = new ArrayList<>();
        hiveOptions = new ArrayList<>();
        TextView userDisplayName = findViewById(R.id.displayName);

        hiveIds= new ArrayList<>();
        hiveOptions = new ArrayList<>();
        displayName = (TextView) findViewById(R.id.displayName);
        locationPin = (ImageView) findViewById(R.id.locationPin);
        userName = (TextView) findViewById(R.id.userName);
        pollenCount = (TextView) findViewById(R.id.pollenCount);
        displayLocation = (TextView) findViewById(R.id.displayLocation);
        hiveListHeading = (TextView) findViewById(R.id.hiveListHeading);
        bio = (TextView) findViewById(R.id.bio);
        dateJoined = (TextView) findViewById(R.id.dateJoined);
        RecyclerView recyclerView = findViewById(R.id.hiveListRecyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        myAdapter = new MyAdapter(getApplicationContext(), hiveOptions);
        recyclerView.setAdapter(myAdapter);


        ServerRequest serverRequest = new ServerRequest();
        com.example.hivefrontend.ui.profile.Logic.ProfileLogic logic = new com.example.hivefrontend.ui.profile.Logic.ProfileLogic(this, serverRequest);
        logic.displayProfile();

        StorageReference test1 = storageReference.child("profilePictures/" + userId + ".jpg");
        StorageReference test2 = storageReference.child("profileBackgrounds/" + userId + ".jpg");

        GlideApp.with(this)
                .load(test1)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .error(R.drawable.defaulth)
                .into(profilePic);

        GlideApp.with(this)
                .load(test2)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true)
                .error(R.drawable.defaultb)
                .into(header);
    }

    /**
     * Returns the Context of this activity
     * @return The Context of this activity
     */
    @Override
    public Context getProfileContext() {
        return this.getApplicationContext();
    }

    /**
     * Gets the user id of the user's profile currently being viewed
     * @return The user id of the user's profile being viewed
     */
    @Override
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the display name TextView to the given name
     * @param name The name to display
     */
    @Override
    public void setDisplayName(String name) {
        displayName.setText(name);
    }

    /**
     * Sets the location TextView to the given String
     * @param location The location to display
     */
    @Override
    public void setDisplayLocation(String location) {
        displayLocation.setText(location);
    }

    /**
     * Sets the location TextView to invisible
     */
    @Override
    public void setLocationInvisible() {
        displayLocation.setVisibility(View.INVISIBLE);
        locationPin.setVisibility(View.INVISIBLE);
    }

    /**
     * Sets the user name TextView to the given name
     * @param uName The name to display
     */
    @Override
    public void setUserName(String uName) {
        userName.setText(uName);
    }

    /**
     * Sets the bio TextView to the given String
     * @param biography The biography to display
     */
    @Override
    public void setBio(String biography) {
        bio.setText(biography);
    }

    /**
     * Sets the hive list heading TextView using the given String
     * @param your_hives The String used to determine the hive list heading
     */
    @Override
    public void setHiveListHeading(String your_hives) {
        String heading = your_hives + "'s Public Hives:";
        if(your_hives.length() == 0){
            heading = "Public Hives:";
        }
        hiveListHeading.setText(heading);
    }

    /**
     * Adds a hive id to the list of hive ids
     * @param hiveId The hive id to add
     */
    @Override
    public void addHiveId(Integer hiveId) {
        hiveIds.add(hiveId);
    }

    /**
     * Adds a hive name to the list of hive names
     * @param hiveName The hive name to add
     */
    @Override
    public void addToHiveOptions(String hiveName) {
        hiveOptions.add(hiveName);
    }

    /**
     * Notifies the adapter of a data change
     */
    @Override
    public void notifyChangeForAdapter() {
        myAdapter.notifyDataSetChanged();
    }

    /**
     * Sets the pollen count TextView to the given String
     * @param substring String to set pollen count as
     */
    @Override
    public void setPollenCountText(String substring) {
        pollenCount.setText(substring);
    }
}