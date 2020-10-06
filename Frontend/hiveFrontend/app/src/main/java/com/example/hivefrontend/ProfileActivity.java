package com.example.hivefrontend;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hivefrontend.ui.profile.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private ArrayList<Integer> hiveIds;
    private ArrayList<String> hiveOptions;
    private RequestQueue queue;
    private TextView textView;
    private ImageView locationPin;
    private TextView userName;
    private TextView pollenCount;
    private TextView displayLocation;
    private TextView hiveListHeading;
    private TextView bio;
    private TextView dateJoined;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        int userId = getIntent().getIntExtra("userId", -1);

        queue = Volley.newRequestQueue(getApplicationContext());
        hiveIds = new ArrayList<>();
        hiveOptions = new ArrayList<>();
        TextView userDisplayName = findViewById(R.id.displayName);

        hiveIds= new ArrayList<>();
        hiveOptions = new ArrayList<>();
        textView = (TextView) findViewById(R.id.displayName);
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

        getUserMemberships(userId);

    }

    //looks through memberships for this user, if hive is public, adds to list
    private void getUserMemberships(int userId) {

        // Instantiate the RequestQueue.

        String url ="http://10.24.227.37:8080/users/byUserId/" + userId;

        // Server name http://coms-309-tc-03.cs.iastate.edu:8080/posts

        //first request: user information
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            // Get the current user (json object) data
                            String name = response.getString("displayName");

                            // Gets first name out of whole name, but checks if a space exists.
                            String firstName = "";
                            if (name.contains(" ")) {
                                firstName = name.substring(0, name.indexOf(" "));
                            }

                            String uName = response.getString("userName");
                            // Formats username and concatenates an '@' before.
                            String location = response.getString("location");
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
                            bio.setText(response.getString("biography"));
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
                                if(!member.getJSONObject("hive").getString("type").equals("private")) {
                                    Integer hiveId = (Integer) member.getJSONObject("hive").getInt("hiveId");
                                    hiveIds.add(hiveId);
                                    String hiveName = member.getJSONObject("hive").getString("name");
                                    hiveOptions.add(hiveName);
                                }
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

                    }
                });



        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
        queue.add(hiveRequest);
        queue.add(pollenCountRequest);
    }

    //gets information to display about the user
    private void getUserInfoFromId(int userId){

    }


    private void setUserInfo(JSONObject userInfo){

    }
}