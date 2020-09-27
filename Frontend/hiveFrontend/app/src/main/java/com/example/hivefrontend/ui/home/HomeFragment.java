package com.example.hivefrontend.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.hivefrontend.R;
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
    private ArrayList<Integer> hiveIds;
    private ArrayList<String> hiveOptions;
    private ArrayList<Integer> hiveIdsHome;
    private ArrayList<String> hiveOptionsHome;
    private ArrayList<Integer> hiveIdsDiscover;
    private ArrayList<String> hiveOptionsDiscover;
    private ArrayList<JSONObject> postObjects;
    private ArrayList<JSONObject> discoverPostObjects;
    private ArrayList<JSONObject> homePostObjects;
    private HomeAdapter homeAdapter;
    private HomeAdapter discoverAdapter;
    private int selectedTab;

    private RequestQueue queue;

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
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        //Request: hive information of this user
        String url ="http://10.24.227.37:8080/members/byUserId/2"; //for now, getting this user's hive information until we have login functionality

            JsonArrayRequest hiveRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject member = response.getJSONObject(i); //should return user,hive pair
                                    Integer hiveId = (Integer) member.getJSONObject("hive").getInt("hiveId");
                                    hiveIdsHome.add(hiveId);
                                    String hiveName = member.getJSONObject("hive").getString("name");
                                    hiveOptionsHome.add(hiveName);
                                }

                                //here the hives' ids and names have been set appropriately
                                //can use them to get discover page posts and home posts
                                //start with discover page posts--methods to get home page posts called from within
                                getDiscoverPosts();


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.i("jsonAppError", e.toString());
                            }

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.i("volleyAppError", "Error: " + error.getMessage());
                            Log.i("volleyAppError", "VolleyError: " + error);

                        }
                    });

            // Add the request to the RequestQueue.
            queue.add(hiveRequest);

        return root;
    }

    //sorts the discover and home page posts chronologically, notifies the adapters of the changes
    private void sortPosts(){
        Collections.sort(homePostObjects, new PostComparator());
        Collections.sort(discoverPostObjects, new PostComparator());
        homeAdapter.notifyDataSetChanged();
        discoverAdapter.notifyDataSetChanged();
    }

    private void getDiscoverPosts(){
        //request posts from all hives:
        String url ="http://10.24.227.37:8080/posts"; //for now, getting all posts
        JsonArrayRequest hivePostRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i = 0; i <  response.length(); i++){
                                JSONObject post = response.getJSONObject(i); //a post object
                                Integer hiveId =  Integer.valueOf(post.getInt("hiveId"));
                                //if the user is in this hive, should not display their posts on the discover page
                                //if they aren't add to the list
                                if(!hiveIdsHome.contains(hiveId)) {
                                    hiveIdsDiscover.add(hiveId);
                                    discoverPostObjects.add(post);
                                }
                            }
                            //now get hive options for discover page for the adapter to use
                            getDiscoverHives();
                            //post info is set for discover page, now get info for home page
                            getHomePosts();
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                            Log.i("jsonAppError",e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);

                    }
                });

        queue.add(hivePostRequest);
    }

    private void getDiscoverHives(){
        //get each hive
        for(int i = 0; i<hiveIdsDiscover.size(); i++){
            int hiveId = hiveIdsDiscover.get(i);
            //request posts from each hive:
            String url ="http://10.24.227.37:8080/hives/byHiveId/" + hiveId; //for now, getting this user's hive information until we have login functionality
            JsonObjectRequest hiveNameRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try{
                              String name = response.getString("name");
                              hiveOptionsDiscover.add(name);
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                                Log.i("jsonAppError",e.toString());
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.i("volleyAppError","Error: " + error.getMessage());
                            Log.i("volleyAppError","VolleyError: "+ error);
                        }
                    });
            queue.add(hiveNameRequest);
        }
    }

    private void getHomePosts(){
        //get each hive id
        for(int i = 0; i<hiveIdsHome.size(); i++){
            int hiveId = hiveIdsHome.get(i);

            //request posts from each hive:
            String url ="http://10.24.227.37:8080/posts/byHiveId/" + hiveId; //for now, getting this user's hive information until we have login functionality
            JsonArrayRequest hivePostRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try{
                                for(int i = 0; i <  response.length(); i++){
                                    JSONObject post = response.getJSONObject(i); //should a post object
                                    homePostObjects.add(post);
                                }
                                //now have all the posts--must sort chronologically
                                sortPosts();
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                                Log.i("jsonAppError",e.toString());
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.i("volleyAppError","Error: " + error.getMessage());
                            Log.i("volleyAppError","VolleyError: "+ error);

                        }
                    });
            queue.add(hivePostRequest);
        }
    }
}