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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.hivefrontend.EditProfileActivity;
import com.example.hivefrontend.GlideApp;
import com.example.hivefrontend.R;
import com.example.hivefrontend.ui.profile.Logic.ProfileLogic;
import com.example.hivefrontend.ui.profile.Network.ServerRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;

import java.util.ArrayList;

public class ProfileFragment extends Fragment implements IProfileView{

    //private RecyclerView recyclerView;
    //private RecyclerView.Adapter mAdapter;
    //private RecyclerView.LayoutManager layoutManager;
    private ProfileViewModel mViewModel;
    public ArrayList<Integer> hiveIds;
    public ArrayList<String> hiveOptions;
    public int userId = 2;
    private RequestQueue queue;
    private String pollen;
    public TextView displayName;
    public ImageView locationPin;
    public TextView userName;
    public Button editProfile;
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

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //new AppController();
        final View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        profilePic = (ImageView) rootView.findViewById(R.id.profilePicture);
        header = (ImageView) rootView.findViewById(R.id.header);hiveIds= new ArrayList<>();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        hiveOptions = new ArrayList<>();
        displayName = (TextView) rootView.findViewById(R.id.displayName);
        locationPin = (ImageView) rootView.findViewById(R.id.locationPin);
        editProfile = (Button) rootView.findViewById(R.id.editprofile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), EditProfileActivity.class));
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

        try {
            GlideApp.with(this)
                    .load(test1)
                    .into(profilePic);

            GlideApp.with(this)
                    .load(test2)
                    .into(header);

        } catch (Exception e) {
            test1 = storageReference.child("profilePictures/defaultProfile.jpg");
            test2 = storageReference.child("profileBackgrounds/defaultBackground.jpg");
            GlideApp.with(this)
                    .load(test1)
                    .into(profilePic);

            GlideApp.with(this)
                    .load(test2)
                    .into(header);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel

    }

    @Override
    public Context getProfileContext() {
        return this.getContext();
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public void setDisplayName(String name) {
        displayName.setText(name);
    }

    @Override
    public void setDisplayLocation(String location) {
        displayLocation.setText(location);
    }

    @Override
    public void setLocationInvisible() {
        displayLocation.setVisibility(View.INVISIBLE);
        locationPin.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setUserName(String uName) {
        userName.setText(uName);
    }

    @Override
    public void setBio(String biography) {
        bio.setText(biography);
    }

    @Override
    public void setHiveListHeading(String your_hives) {
        hiveListHeading.setText("Your Hives:");
    }

    @Override
    public void addHiveId(Integer hiveId) {
        hiveIds.add(hiveId);
    }

    @Override
    public void addToHiveOptions(String hiveName) {
        hiveOptions.add(hiveName);
    }

    @Override
    public void notifyChangeForAdapter() {
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPollenCountText(String substring) {
        pollenCount.setText(substring);
    }
}