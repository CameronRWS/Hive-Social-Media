package com.example.hivefrontend.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hivefrontend.PostDetailsActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.SettingsActivity;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.home.Logic.HomeLogic;
import com.example.hivefrontend.ui.profile.MyAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public static ArrayList<Integer> hiveIds;
    public static ArrayList<String> hiveOptions;
    public static ArrayList<Integer> hiveIdsHome;
    public static ArrayList<String> hiveOptionsHome;
    public static ArrayList<Integer> hiveIdsDiscover;
    public static ArrayList<String> hiveOptionsDiscover;
    public static ArrayList<JSONObject> postObjects;
    public static ArrayList<JSONObject> discoverPostObjects;
    public static ArrayList<JSONObject> homePostObjects;
    public static HomeAdapter homeAdapter;
    public static HomeAdapter discoverAdapter;
    public int selectedTab;


    public static HomeLogic logic;
    public static Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        hiveIdsHome = new ArrayList<>();
        hiveOptionsHome = new ArrayList<>();
        hiveIdsDiscover = new ArrayList<>();
        hiveOptionsDiscover = new ArrayList<>();
        hiveIds= new ArrayList(hiveIdsHome);
        hiveOptions = new ArrayList(hiveOptionsHome);


        homePostObjects = new ArrayList<>();
        postObjects = new ArrayList(homePostObjects);
        discoverPostObjects = new ArrayList<>();

        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });


        final RecyclerView recyclerView = root.findViewById(R.id.homePostRecyler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        homeAdapter = new HomeAdapter(getActivity().getApplicationContext(), homePostObjects,hiveIdsHome,hiveOptionsHome);
        discoverAdapter = new HomeAdapter(getActivity().getApplicationContext(), discoverPostObjects,hiveIdsDiscover,hiveOptionsDiscover);
        recyclerView.setAdapter(homeAdapter);

        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
        //set on tab selected listener--on tab reselect or select, set the recyclerView's adapter to home/discover adapter as needed
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectedTab = tab.getPosition();
                if(selectedTab == 0){
                    recyclerView.setAdapter(homeAdapter);
                }
                else{
                    recyclerView.setAdapter(discoverAdapter);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                selectedTab = tab.getPosition();

                if(selectedTab == 0){
                    recyclerView.setAdapter(homeAdapter);
                }
                else{
                    recyclerView.setAdapter(discoverAdapter);
                }
            }
        });

        logic = new HomeLogic(this);
        logic.setUserHives();

        context = root.getContext();
        return root;
    }

    public static void likePost(final int postId){
        logic.likePostLogic(postId);
    }

    @Override
    public void onResume() {
        super.onResume();
        logic.onPageResume();
    }



}