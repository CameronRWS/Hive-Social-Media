package com.example.hivefrontend.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hivefrontend.R;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.ui.home.Logic.HomeLogic;
import com.example.hivefrontend.ui.home.Network.ServerRequest;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment implements IHomeView{

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
    public int userId;


    public static HomeLogic logic;
    public static Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        userId = SharedPrefManager.getInstance(this.getContext()).getUser().getId();
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


        ServerRequest serverRequest = new ServerRequest();
        logic = new HomeLogic(this, serverRequest);
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

    public int getUserId(){
        return userId;
    }

    public void addToHiveIdsHome(int hiveId){
        hiveIdsHome.add(hiveId);
    }
    public void addToHiveOptionsHome(String hiveName){
        hiveOptionsHome.add(hiveName);
    }
    public void addToHiveIdsDiscover(int hiveId){
        hiveIdsDiscover.add(hiveId);
    }
    public void addToHiveOptionsDiscover(String hiveName){
        hiveOptionsDiscover.add(hiveName);
    }

    @Override
    public void notifyDataChange() {
        homeAdapter.notifyDataSetChanged();
        discoverAdapter.notifyDataSetChanged();
    }

    public Context getHomeContext(){
        return this.getContext();
    }

    public void clearData(){
        discoverPostObjects.clear();
        homePostObjects.clear();
        hiveIdsDiscover.clear();
        hiveOptionsDiscover.clear();
        hiveIdsHome.clear();
        hiveOptionsHome.clear();
    }

    @Override
    public void sortPosts() {
        Collections.sort(homePostObjects, new PostComparator());
        Collections.sort(discoverPostObjects, new PostComparator());
    }

    @Override
    public void addToDiscoverPosts(JSONObject post) {
        discoverPostObjects.add(post);
    }

    @Override
    public void addToHomePosts(JSONObject post) {
        homePostObjects.add(post);
    }

    @Override
    public ArrayList<Integer> getHiveIdsHome() {
        return hiveIdsHome;
    }

    @Override
    public ArrayList<String> getHiveOptionsHome() {
        return hiveOptionsHome;
    }

    @Override
    public ArrayList<Integer> getHiveIdsDiscover() {
        return hiveIdsDiscover;
    }

    @Override
    public ArrayList<String> getHiveOptionsDiscover() {
        return hiveOptionsDiscover;
    }


}