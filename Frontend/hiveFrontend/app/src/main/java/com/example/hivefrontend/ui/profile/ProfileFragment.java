package com.example.hivefrontend.ui.profile;

import androidx.lifecycle.ViewModelProviders;

<<<<<<< HEAD
import android.content.Context;
=======
import android.content.Intent;
>>>>>>> edit-profile-screen
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
=======
import android.widget.ArrayAdapter;
import android.widget.Button;
>>>>>>> edit-profile-screen
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
<<<<<<< HEAD
import com.example.hivefrontend.R;
import com.example.hivefrontend.ui.profile.Logic.ProfileLogic;
import com.example.hivefrontend.ui.profile.Network.ServerRequest;
=======
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hivefrontend.EditProfileActivity;
import com.example.hivefrontend.LoginActivity;
import com.example.hivefrontend.R;
import com.example.hivefrontend.RegisterActivity;
>>>>>>> edit-profile-screen

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
<<<<<<< HEAD
        displayName = (TextView) rootView.findViewById(R.id.displayName);
        locationPin = (ImageView) rootView.findViewById(R.id.locationPin);
        userName = (TextView) rootView.findViewById(R.id.userName);
        pollenCount = (TextView) rootView.findViewById(R.id.pollenCount);
        displayLocation = (TextView) rootView.findViewById(R.id.displayLocation);
        hiveListHeading = (TextView) rootView.findViewById(R.id.hiveListHeading);
        bio = (TextView) rootView.findViewById(R.id.bio);
        dateJoined = (TextView) rootView.findViewById(R.id.dateJoined);
        recyclerView = rootView.findViewById(R.id.hiveListRecyler);
=======
        final TextView textView = (TextView) rootView.findViewById(R.id.displayName);
        final ImageView locationPin = (ImageView) rootView.findViewById(R.id.locationPin);
        final TextView userName = (TextView) rootView.findViewById(R.id.userName);
        final TextView pollenCount = (TextView) rootView.findViewById(R.id.pollenCount);
        final TextView displayLocation = (TextView) rootView.findViewById(R.id.displayLocation);
        final TextView hiveListHeading = (TextView) rootView.findViewById(R.id.hiveListHeading);
        final TextView bio = (TextView) rootView.findViewById(R.id.bio);
        final Button editProfile = (Button) rootView.findViewById(R.id.editprofile);
        RecyclerView recyclerView = rootView.findViewById(R.id.hiveListRecyler);
>>>>>>> edit-profile-screen
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        myAdapter = new MyAdapter(getActivity().getApplicationContext(), hiveOptions);
        recyclerView.setAdapter(myAdapter);

<<<<<<< HEAD
        ServerRequest serverRequest = new ServerRequest();
        ProfileLogic logic = new ProfileLogic(this, serverRequest);
        logic.displayProfile();
=======
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url ="http://10.24.227.37:8080/users";

        // Server name http://coms-309-tc-03.cs.iastate.edu:8080/posts

        //first request: user information
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            JSONObject user1 = response.getJSONObject(userId);
                            // Get the current user (json object) data
                            String name = user1.getString("displayName");

                            // Gets first name out of whole name, but checks if a space exists.
                            String firstName = "";
                            if (name.contains(" ")) {
                                firstName = name.substring(0, name.indexOf(" "));
                            }

                            String uName = user1.getString("userName");
                            // Formats username and concatenates an '@' before.
                            String location = user1.getString("location");
                            // Formats username and concatenates an '@' before.
                            uName = uName.toLowerCase();
                            uName = "@" + uName;
                            // Display the formatted json data in text view
                            textView.setText(name);
                            displayLocation.setText(location);
                            // get rid of the location pin if the location is null
                            if (location == "null")
                            {
                                displayLocation.setVisibility(View.INVISIBLE);
                                locationPin.setVisibility(View.INVISIBLE);
                            }
                            userName.setText(uName);
                            bio.setText(user1.getString("biography"));
                            // TODO: replace '(4)' with actual count.
                            if (firstName.length() == 0)
                            {
                                hiveListHeading.setText("Their Public Hives:");
                            }
                            else {
                                hiveListHeading.setText((firstName + "'s Public Hives:"));
                            }
                            //dateJoined.setText(user1.getString("dateCreated"));
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

                        textView.setText("Error.");

                    }
                });
        //second request: hive information
        url ="http://10.24.227.37:8080/members/byUserId/" + userId; //for now, getting this user's hive information until we have login functionality

        JsonArrayRequest hiveRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i = 0; i < response.length(); i++){
                                JSONObject member = response.getJSONObject(i); //should return user,hive pair
                                Integer hiveId = (Integer) member.getJSONObject("hive").getInt("hiveId");
                                hiveIds.add(hiveId);
                                String hiveName =  member.getJSONObject("hive").getString("name");
                                hiveOptions.add(hiveName);
                            }
                            //here the hives' ids and names have been set appropriately

//                           RecyclerView recyclerView = rootView.findViewById(R.id.hiveListRecyler);
//                           recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//                           MyAdapter myAdapter = new MyAdapter(getActivity().getApplicationContext(), hiveOptions);
//                           recyclerView.setAdapter(myAdapter);

                           myAdapter.notifyDataSetChanged();

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

                        textView.setText("Error.");

                    }
                });

        //third request: pollen cunt
        url ="http://10.24.227.37:8080/likeCount/byUserId/" + userId; //for now, getting this user's hive information until we have login functionality

                StringRequest pollenCountRequest = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        pollenCount.setText(response.substring(13, response.length() - 1));

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.i("volleyAppError","Error: " + error.getMessage());
                        Log.i("volleyAppError","VolleyError: "+ error);

                        textView.setText("Error.");

                    }
                });



    // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
        queue.add(hiveRequest);
        queue.add(pollenCountRequest);
>>>>>>> edit-profile-screen

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