package com.example.hivefrontend.ui.profile;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hivefrontend.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profile_fragment, container, false);

        final TextView textView = (TextView) rootView.findViewById(R.id.displayName);
        final ImageView locationPin = (ImageView) rootView.findViewById(R.id.locationPin);
        final TextView userName = (TextView) rootView.findViewById(R.id.userName);
        final TextView pollenCount = (TextView) rootView.findViewById(R.id.pollenCount);
        final TextView displayLocation = (TextView) rootView.findViewById(R.id.displayLocation);
        final TextView hiveListHeading = (TextView) rootView.findViewById(R.id.hiveListHeading);
        final TextView bio = (TextView) rootView.findViewById(R.id.bio);
        final TextView dateJoined = (TextView) rootView.findViewById(R.id.dateJoined);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url ="http://10.24.227.37:8080/users";

        // Server name http://coms-309-tc-03.cs.iastate.edu:8080/posts


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            JSONObject user1 = response.getJSONObject(3);
                            // Get the current user (json object) data
                            String name = user1.getString("displayName");

                            // Gets first name out of whole name, but checks if a space exists.
                            String firstName = "";
                            if (name.contains(" ")) {
                                firstName = name.substring(0, name.indexOf(" "));
                            }

                            String uName = user1.getString("userName");
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
                                hiveListHeading.setText("Their Public Hives (4)");
                            }
                            else {
                                hiveListHeading.setText((firstName + "'s Public Hives (4)"));
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



// Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel


    }



}