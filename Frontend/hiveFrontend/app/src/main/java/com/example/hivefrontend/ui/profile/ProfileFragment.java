package com.example.hivefrontend.ui.profile;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.hivefrontend.EditProfile.EditProfileActivity;
import com.example.hivefrontend.GlideApp;
import com.example.hivefrontend.HiveRequests.HiveRequestsActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.ui.profile.Logic.ProfileLogic;
import com.example.hivefrontend.ui.profile.Network.ServerRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Fragment showing the logged in user's profile.
 * Accessible from the rightmost tab of the bottom navigation.
 */
public class ProfileFragment extends Fragment implements IProfileView{


    private ProfileViewModel mViewModel;
    /**
     * List of hive ids for this user
     */
    private ArrayList<Integer> hiveIds;
    /**
     * List of hive names for this user
     */
    private ArrayList<String> hiveOptions;
    /**
     * This user's user id
     */
    private int userId;

    /**
     * TextView for the user's display name
     */
    private TextView displayName;
    /**
     * ImageView for the user's location
     */
    private ImageView locationPin;
    /**
     * TextView for the user's user name
     */
    private TextView userName;
    /**
     * Edit profile button
     */
    private Button editProfile;
    /**
     * TextView for the user's pollen count
     */
    private TextView pollenCount;
    /**
     * TextView for the user's location
     */
    private TextView displayLocation;
    /**
     * TextView for the hive list heading
     */
    private TextView hiveListHeading;
    /**
     * TextView for the user's bio
     */
    private TextView bio;
    private TextView dateJoined;
    /**
     * RecyclerView for this user's hives
     */
    private RecyclerView recyclerView;
    /**
     * Adapter for the RecyclerView
     */
    private MyAdapter myAdapter;

    /**
     * ImageView for this user's profile picture
     */
    private ImageView profilePic;
    /**
     * ImageView for this user's profile picture
     */
    private ImageView header;
    public ImageView hiveProfile;
    public ImageView hiveBanner;
    private Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    /**
     * Create hive button
     */
    private Button createHive;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    /**
     * Upon creation, sets up screen structure and calls the logic class to get profile information to display.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //new AppController();
        userId = SharedPrefManager.getInstance(this.getContext()).getUser().getId();
        final View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        profilePic = (ImageView) rootView.findViewById(R.id.profilePicture);

        header = (ImageView) rootView.findViewById(R.id.header);
        hiveProfile = (ImageView) rootView.findViewById(R.id.hiveCardPicture);
        hiveBanner = (ImageView) rootView.findViewById(R.id.hiveCardHeader);
        hiveIds= new ArrayList<>();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        hiveOptions = new ArrayList<>();
        // Log.i("userId", "userId is" + userId);
        displayName = (TextView) rootView.findViewById(R.id.displayName);
        locationPin = (ImageView) rootView.findViewById(R.id.locationPin);
        editProfile = (Button) rootView.findViewById(R.id.editprofile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), EditProfileActivity.class));
            }
        });

        createHive = rootView.findViewById(R.id.createHive);
        createHive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), HiveRequestsActivity.class));
            }
        });
        userName = (TextView) rootView.findViewById(R.id.userName);
        pollenCount = (TextView) rootView.findViewById(R.id.pollenCount);
        displayLocation = (TextView) rootView.findViewById(R.id.displayLocation);
        hiveListHeading = (TextView) rootView.findViewById(R.id.hiveListHeading);
        bio = (TextView) rootView.findViewById(R.id.bio);
        dateJoined = (TextView) rootView.findViewById(R.id.dateJoined);
        recyclerView = rootView.findViewById(R.id.hiveListRecyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        myAdapter = new MyAdapter(getActivity().getApplicationContext(), hiveOptions);
        recyclerView.setAdapter(myAdapter);

        ServerRequest serverRequest = new ServerRequest();
        ProfileLogic logic = new ProfileLogic(this, serverRequest);
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
        
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel

    }

    /**
     * Returns the Context of this ProfileFragment
     * @return Context of this ProfileFragment
     */
    @Override
    public Context getProfileContext() {
        return this.getContext();
    }

    /**
     * Returns the user id of the current user
     * @return The user id of the current user
     */
    @Override
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the display name TextView to the given name
     * @param name The display name of this user
     */
    @Override
    public void setDisplayName(String name) {
        displayName.setText(name);
    }

    /**
     * Sets the location TextView to the given location
     * @param location The location of this user
     */
    @Override
    public void setDisplayLocation(String location) {
        displayLocation.setText(location);
    }

    /**
     * Sets the location TextView and pin invisible
     */
    @Override
    public void setLocationInvisible() {
        displayLocation.setVisibility(View.INVISIBLE);
        locationPin.setVisibility(View.INVISIBLE);
    }

    /**
     * Sets the user name TextView to the given name
     * @param uName The user name of this user
     */
    @Override
    public void setUserName(String uName) {
        userName.setText(uName);
    }

    /**
     * Sets the bio TextView to the given bio
     * @param biography The bio of this user
     */
    @Override
    public void setBio(String biography) {
        bio.setText(biography);
    }

    /**
     * Sets the text of the hive list heading
     * @param your_hives String to set the text to
     */
    @Override
    public void setHiveListHeading(String your_hives) {
        hiveListHeading.setText("Your Hives:");
    }

    /**
     * Adds  the given id to the list of this user's hive ids
     * @param hiveId The hive id to add
     */
    @Override
    public void addHiveId(Integer hiveId) {
        hiveIds.add(hiveId);
    }

    /**
     * Adds the given name to the list of hive names
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
     * Sets the pollen count text to the given String
     * @param substring The pollen count text
     */
    @Override
    public void setPollenCountText(String substring) {
        pollenCount.setText(substring);
    }
}