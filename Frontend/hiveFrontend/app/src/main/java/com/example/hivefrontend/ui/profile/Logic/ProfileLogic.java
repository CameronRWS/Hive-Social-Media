package com.example.hivefrontend.ui.profile.Logic;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.VolleyError;
import com.example.hivefrontend.ui.profile.Network.ServerRequest;
import com.example.hivefrontend.ui.profile.ProfileFragment;
import com.example.hivefrontend.ui.profile.ProfileVolleyListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileLogic implements ProfileVolleyListener{
    ProfileFragment profile;
    public Context context;
    public int userId;
    ServerRequest server;

    public ProfileLogic(ProfileFragment p, ServerRequest serverRequest){
        this.profile = p;
        context = p.getContext();
        userId = p.userId;
        serverRequest.addVolleyListener(this);
    }

    public int getUserId(){
        return userId;
    }

    public Context getProfileContext(){
        return profile.getContext();
    }
    public void displayProfile(){
        server.userInfoRequest();
        server.hiveListRequest();
        server.pollenCountRequest();
    }

    public void onUserInfoSuccess(JSONArray response){
        try{
            JSONObject user1 = response.getJSONObject(profile.userId);
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
            profile.displayName.setText(name);
            profile.displayLocation.setText(location);
            // get rid of the location pin if the location is null
            if (location == "null")
            {
                profile.displayLocation.setVisibility(View.INVISIBLE);
                profile.locationPin.setVisibility(View.INVISIBLE);
            }
            profile.userName.setText(uName);
            profile.bio.setText(user1.getString("biography"));
            // TODO: replace '(4)' with actual count.
            if (firstName.length() == 0)
            {
                profile.hiveListHeading.setText("Their Public Hives:");
            }
            else {
                profile.hiveListHeading.setText((firstName + "'s Public Hives:"));
            }
            //dateJoined.setText(user1.getString("dateCreated"));
        }
        catch (JSONException e){
            e.printStackTrace();
            Log.i("jsonAppError",e.toString());
        }
    }

    public void onUserInfoError(VolleyError error){
        // TODO: Handle error
        Log.i("volleyAppError","Error: " + error.getMessage());
        Log.i("volleyAppError","VolleyError: "+ error);
        profile.displayName.setText("Error.");
    }

    public void onHiveListSuccess(JSONArray response){
        try{
            for(int i = 0; i < response.length(); i++){
                JSONObject member = response.getJSONObject(i); //should return user,hive pair
                Integer hiveId = (Integer) member.getJSONObject("hive").getInt("hiveId");
                profile.hiveIds.add(hiveId);
                String hiveName =  member.getJSONObject("hive").getString("name");
                profile.hiveOptions.add(hiveName);
            }
            //here the hives' ids and names have been set appropriately

//                           RecyclerView recyclerView = rootView.findViewById(R.id.hiveListRecyler);
//                           recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//                           MyAdapter myAdapter = new MyAdapter(getActivity().getApplicationContext(), hiveOptions);
//                           recyclerView.setAdapter(myAdapter);

            profile.myAdapter.notifyDataSetChanged();

        }
        catch (JSONException e){
            e.printStackTrace();
            Log.i("jsonAppError",e.toString());
        }
    }

    public void onHiveListError(VolleyError error){
        // TODO: Handle error
        Log.i("volleyAppError","Error: " + error.getMessage());
        Log.i("volleyAppError","VolleyError: "+ error);

        profile.displayName.setText("Error.");
    }
    public void pollenCountSuccess(String response){
        profile.pollenCount.setText(response.substring(13, response.length() - 1));
    }

    public void pollenCountError(VolleyError error){
        // TODO: Handle error
        Log.i("volleyAppError","Error: " + error.getMessage());
        Log.i("volleyAppError","VolleyError: "+ error);

        profile.displayName.setText("Error.");
    }


}
