package com.example.hivefrontend.ui.profile.Network;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.hivefrontend.VolleySingleton;
import com.example.hivefrontend.ui.profile.ProfileFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerRequest {
    private String tag_json_obj = "json_obj_req";

    private ProfileFragment profile;

    public ServerRequest (ProfileFragment r) {
        this.profile = r;
    }



    public void profileRequest(){
        final TextView textView = (TextView) profile.displayName;
            String url ="http://10.24.227.37:8080/users";

            // Server name http://coms-309-tc-03.cs.iastate.edu:8080/posts

            //first request: user information
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
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
            url ="http://10.24.227.37:8080/members/byUserId/" + profile.userId; //for now, getting this user's hive information until we have login functionality

            JsonArrayRequest hiveRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
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
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error
                            Log.i("volleyAppError","Error: " + error.getMessage());
                            Log.i("volleyAppError","VolleyError: "+ error);

                            textView.setText("Error.");

                        }
                    });

            //third request: pollen count
            url ="http://10.24.227.37:8080/likeCount/byUserId/" + profile.userId; //for now, getting this user's hive information until we have login functionality

            StringRequest pollenCountRequest = new StringRequest
                    (Request.Method.GET, url, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            profile.pollenCount.setText(response.substring(13, response.length() - 1));

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

        VolleySingleton.getInstance(profile.getContext()).addToRequestQueue(jsonArrayRequest);
        VolleySingleton.getInstance(profile.getContext()).addToRequestQueue(hiveRequest);
        VolleySingleton.getInstance(profile.getContext()).addToRequestQueue(pollenCountRequest);

        //AppController.getInstance().addToRequestQueue(jsonArrayRequest, tag_json_obj);
        //AppController.getInstance().addToRequestQueue(hiveRequest, tag_json_obj);
        //AppController.getInstance().addToRequestQueue(pollenCountRequest, tag_json_obj);

        }

}
