package com.example.hivefrontend.ui.profile.Logic;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hivefrontend.ui.profile.IProfileServerRequest;
import com.example.hivefrontend.ui.profile.IProfileView;
import com.example.hivefrontend.ui.profile.MyAdapter;
import com.example.hivefrontend.ui.profile.ProfileVolleyListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileLogic implements ProfileVolleyListener{
    IProfileView profileView;
    public Context context;
    public int userId;
    IProfileServerRequest server;
    MyAdapter adapter;
    private int memberCount;
    private String hiveDescrip;

    public ProfileLogic(IProfileView p, IProfileServerRequest serverRequest){
        this.profileView = p;
        context = p.getProfileContext();
        userId = p.getUserId();
        this.server = serverRequest;
        server.addVolleyListener(this);
    }


    public int getUserId(){
        return userId;
    }

    public Context getProfileContext(){
        return profileView.getProfileContext();
    }
    public void displayProfile(){
        server.userInfoRequest();
        server.hiveListRequest();
        server.pollenCountRequest();
    }

    public void onUserInfoSuccess(JSONArray response){
        try{
            JSONObject user1 = response.getJSONObject(userId-1);
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


            profileView.setDisplayName(name);

            //profile.displayLocation.setText(location);
            profileView.setDisplayLocation(location);

            // get rid of the location pin if the location is null
            if (location == "null")
            {
                profileView.setLocationInvisible();
            }
            //profile.userName.setText(uName);
            profileView.setUserName(uName);
            //profile.bio.setText(user1.getString("biography"));
            profileView.setBio(user1.getString("biography"));
            // TODO: replace '(4)' with actual count.

            profileView.setHiveListHeading(firstName);

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
        //profile.displayName.setText("Error.");
        profileView.setDisplayName("Error.");
    }

    public void onHiveListSuccess(JSONArray response){
        try{
            for(int i = 0; i < response.length(); i++){
                JSONObject member = response.getJSONObject(i); //should return user,hive pair
                Integer hiveId = (Integer) member.getJSONObject("hive").getInt("hiveId");
                //profile.hiveIds.add(hiveId);
                profileView.addHiveId(hiveId);
                String hiveName =  member.getJSONObject("hive").getString("name");
                //profile.hiveOptions.add(hiveName);
                profileView.addToHiveOptions(hiveName);
            }
            //here the hives' ids and names have been set appropriately

//                           RecyclerView recyclerView = rootView.findViewById(R.id.hiveListRecyler);
//                           recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//                           MyAdapter myAdapter = new MyAdapter(getActivity().getApplicationContext(), hiveOptions);
//                           recyclerView.setAdapter(myAdapter);

            //profile.myAdapter.notifyDataSetChanged();
            profileView.notifyChangeForAdapter();

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

        //profile.displayName.setText("Error.");
        profileView.setDisplayName("Error.");
    }
    public void pollenCountSuccess(String response){
        //profile.pollenCount.setText(response.substring(13, response.length() - 1));
        profileView.setPollenCountText(response.substring(13,response.length()-1));
    }

    public void pollenCountError(VolleyError error){
        // TODO: Handle error
        Log.i("volleyAppError","Error: " + error.getMessage());
        Log.i("volleyAppError","VolleyError: "+ error);

        //profile.displayName.setText("Error.");
        profileView.setDisplayName("Error.");
    }

    @Override
    public void onFetchMemberCountSuccess(JSONArray response, String hiveName) {
        try {

            for(int i = 0; i < response.length(); i++) {
                JSONObject member = response.getJSONObject(i);
                if (member.getJSONObject("hive").getString("name").equals(hiveName)) {
                    memberCount++;
                    hiveDescrip = member.getJSONObject("hive").getString("description");
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFetchHiveDescriptionSuccess(JSONArray response, String hiveName) {
        try {
            for(int i = 0; i < response.length(); i++) {
                JSONObject member = response.getJSONObject(i);
                if (member.getString("name").equals(hiveName)) {
                    Log.i("princess", "name: " + member.getString("name") + "description: " + member.getString("description"));
                    hiveDescrip = member.getString("description");
                    break;
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getMemberCount() {return memberCount;}


    @Override
    public String getHiveDescrip() {return hiveDescrip;}

}
