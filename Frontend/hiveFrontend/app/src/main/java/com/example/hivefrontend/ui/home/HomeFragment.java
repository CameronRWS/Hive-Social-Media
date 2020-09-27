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
    private ArrayList<JSONObject> postObjects;
    private ArrayList<JSONObject> discoverPostObjects;
    private HomeAdapter homeAdapter;
    private HomeAdapter discoverAdapter;
    private int selectedTab;

private RequestQueue queue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        hiveIds= new ArrayList<>();
        hiveOptions = new ArrayList<>();
        postObjects = new ArrayList<>();
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
        homeAdapter = new HomeAdapter(getActivity().getApplicationContext(), postObjects,hiveIds,hiveOptions); //dummy data for now
        discoverAdapter = new HomeAdapter(getActivity().getApplicationContext(), discoverPostObjects,hiveIds,hiveOptions);
        recyclerView.setAdapter(homeAdapter);

        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabLayout);
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
                Log.i("selected tab position:", ""+selectedTab);
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
        String url ="http://10.24.227.37:8080/members/byUserId/1"; //for now, getting this user's hive information until we have login functionality

        if(selectedTab == 0) {
            JsonArrayRequest hiveRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject member = response.getJSONObject(i); //should return user,hive pair
                                    Integer hiveId = (Integer) member.getJSONObject("hive").getInt("hiveId");
                                    hiveIds.add(hiveId);
                                    String hiveName = member.getJSONObject("hive").getString("name");
                                    hiveOptions.add(hiveName);
                                }
                                //here the hives' ids and names have been set appropriately
                                //must get posts from all the hives this user has, then sort them
                                getInfoFromPosts();


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

        }
        else if(selectedTab ==1){

        }
        return root;
    }

    private void sortPosts(){
        Collections.sort(postObjects, new PostComparator());
        homeAdapter.notifyDataSetChanged();
    }

    private void getInfoFromPosts(){

        //get each hive
        for(int i = 0; i<hiveIds.size(); i++){
            int hiveId = hiveIds.get(i);

            //request posts from each hive:
            String url ="http://10.24.227.37:8080/posts/byHiveId/" + hiveId; //for now, getting this user's hive information until we have login functionality
            Log.i("status", "just before buzz request");
            JsonArrayRequest hivePostRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try{
                                for(int i = 0; i <  response.length(); i++){
                                    JSONObject post = response.getJSONObject(i); //should a post object
                                    postObjects.add(post);
                                }
                                //now have all the posts--must sort chronologically
                                sortPosts();
                                homeAdapter.notifyDataSetChanged();
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