package com.example.hivefrontend.ui.profile;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.hivefrontend.R;
import com.example.hivefrontend.ui.profile.Logic.ProfileLogic;
import com.example.hivefrontend.ui.profile.Network.ServerRequest;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    //private RecyclerView recyclerView;
    //private RecyclerView.Adapter mAdapter;
    //private RecyclerView.LayoutManager layoutManager;
    private ProfileViewModel mViewModel;
    public ArrayList<Integer> hiveIds;
    public ArrayList<String> hiveOptions;
    public int userId = 1;
    private RequestQueue queue;
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

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //new AppController();
        final View rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        hiveIds= new ArrayList<>();
        hiveOptions = new ArrayList<>();
        displayName = (TextView) rootView.findViewById(R.id.displayName);
        locationPin = (ImageView) rootView.findViewById(R.id.locationPin);
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

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel


    }



}