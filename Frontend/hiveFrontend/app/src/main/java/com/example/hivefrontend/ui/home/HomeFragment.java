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

/**
 * Fragment for leftmost tab on the bottom navigation, containing the home and discover tabs
 */
public class HomeFragment extends Fragment implements IHomeView{

    private HomeViewModel homeViewModel;

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

    /**
     * Method called from the adapter when a post is liked. Calls the logic method to handle the logic behind liking a post
     * @param postId The id of the post to be liked
     */
    public static void likePost(final int postId){
        logic.likePostLogic(postId);
    }

    /**
     * Makes a call to the logic class to handle page resume
     */
    @Override
    public void onResume() {
        super.onResume();
        logic.onPageResume();
    }

    /**
     * Gets the user id of the logged in user
     * @return The user id of the logged in user
     */
    public int getUserId(){
        return userId;
    }

    /**
     * Adds to the list of hiveIds for hives to display on the home page (hives this user is a part of)
     * @param hiveId The hive id to add
     */
    public void addToHiveIdsHome(int hiveId){
        hiveIdsHome.add(hiveId);
    }

    /**
     * Adds to the hive names to be shown on the home page
     * @param hiveName Name of the hive to be added
     */
    public void addToHiveOptionsHome(String hiveName){
        hiveOptionsHome.add(hiveName);
    }

    /**
     * Adds to the list of hiveIds for hives to display on the discover page (hives this user is not a part of)
     * @param hiveId The hive id to add
     */
    public void addToHiveIdsDiscover(int hiveId){
        hiveIdsDiscover.add(hiveId);
    }

    /**
     * Adds to the hive names to be shown on the discover page
     * @param hiveName The hive name to add
     */
    public void addToHiveOptionsDiscover(String hiveName){
        hiveOptionsDiscover.add(hiveName);
    }

    /**
     * Notifies the home adapter and discover adapter that the data has been changed. Called after the user interacts with the posts or when the page resumes.
     */
    @Override
    public void notifyDataChange() {
        homeAdapter.notifyDataSetChanged();
        discoverAdapter.notifyDataSetChanged();
    }

    /**
     * Returns the context for the HomeFragment
     * @return The context for this fragment
     */
    public Context getHomeContext(){
        return this.getContext();
    }

    /**
     * Clears the ArrayLists of posts, hive ids, and hive options for both the home and discover tab.
     * Called before a request to get the data again to update any changed data.
     */
    public void clearData(){
        discoverPostObjects.clear();
        homePostObjects.clear();
        hiveIdsDiscover.clear();
        hiveOptionsDiscover.clear();
        hiveIdsHome.clear();
        hiveOptionsHome.clear();
    }

    /**
     * Using the PostComparator, sorts the home and discover posts chronologically. Newer posts will appear on top.
     */
    @Override
    public void sortPosts() {
        Collections.sort(homePostObjects, new PostComparator());
        Collections.sort(discoverPostObjects, new PostComparator());
    }

    /**
     * Adds to the list of posts for the discover tab.
     * @param post The post to add to the discover tab
     */
    @Override
    public void addToDiscoverPosts(JSONObject post) {
        discoverPostObjects.add(post);
    }

    /**
     * Adds to the list of posts for the home tab.
     * @param post The post to add to the home tab
     */
    @Override
    public void addToHomePosts(JSONObject post) {
        homePostObjects.add(post);
    }

    /**
     * Returns back the hive ids for the home tab (the hives this user is a member of)
     * @return The home tab hive ids
     */
    @Override
    public ArrayList<Integer> getHiveIdsHome() {
        return hiveIdsHome;
    }

    /**
     * Returns back the hive names for the home tab (the hives this user is a member of)
     * @return The home tab hive names
     */
    @Override
    public ArrayList<String> getHiveOptionsHome() {
        return hiveOptionsHome;
    }

    /**
     * Returns back the hive ids for the discover tab (the hives this user is not a member of)
     * @return The discover tab hive ids
     */
    @Override
    public ArrayList<Integer> getHiveIdsDiscover() {
        return hiveIdsDiscover;
    }

    /**
     * Returns back the hive names for the discover tab (the hives this user is not a member of)
     * @return The discover tab hive names
     */
    @Override
    public ArrayList<String> getHiveOptionsDiscover() {
        return hiveOptionsDiscover;
    }


}