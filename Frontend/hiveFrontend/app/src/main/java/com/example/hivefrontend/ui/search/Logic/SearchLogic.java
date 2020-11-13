package com.example.hivefrontend.ui.search.Logic;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hivefrontend.ui.search.ISearchView;
import com.example.hivefrontend.ui.search.Server.ISearchServerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class to handle logic for SearchFragment
 */
public class SearchLogic implements ISearchVolleyListener {


    /**
     * ISearchView to use for this SearchLogic instance
     */
    private ISearchView s;
    /**
     * ISearchServerRequest to use for this SearchLogic instance.
     * Called to handle server requests.
     */
    private ISearchServerRequest server;

    /**
     * Creates a SearchLogic instance from the given ISearchView and ISearchServerRequest.
     * @param s The ISearchView for this SearchLogic instance
     * @param req The ISearchServerRequest for this SearchLogic instance
     */
    public SearchLogic(ISearchView s, ISearchServerRequest req){
        this.s = s;
        this.server= req;
        server.addVolleyListener(this);
    }

    /**
     * Calls the server class to get the user's hives
     */
    public void getHives(){
        server.getUserHives(s.getUserId());
    }

    /**
     * Calls the ISearchView to get the Context
     * @return The Context for ISearchView
     */
    @Override
    public Context getSearchContext() {
        return s.getSearchContext();
    }

    /**
     * Called upon error in a server request, prints the error message
     * @param error The VolleyError received
     */
    @Override
    public void onError(VolleyError error) {
        Log.i("volleyAppError", "Error: " + error.getMessage());
        Log.i("volleyAppError", "VolleyError: " + error);
    }

    /**
     * Called upon successful server request to get user hives.
     * Adds the hive ids to the list of user hives and calls the server to get all other hives.
     * @param response Response received from the server containing user/hive member objects
     */
    @Override
    public void onHiveRequestSuccess(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject member = response.getJSONObject(i); //should return user,hive pair
                Integer hiveId = (Integer) member.getJSONObject("hive").getInt("hiveId");
                s.addToUserHives(hiveId);
                Log.i(" hive of this user : ", " " + hiveId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("jsonAppError", e.toString());
        }
        server.getOtherHives();
    }

    /**
     * Called upon successful request to get all other hives
     * Handles the logic of determining which hives in the response to display
     * @param response Response from the server containing all hives
     * @throws JSONException
     */
    @Override
    public void onGetOtherHivesRequestSuccess(JSONArray response) throws JSONException {
        for(int i = 0; i<response.length(); i++){
            JSONObject hive = response.getJSONObject(i);
            boolean viewable = !hive.getString("type").equals("private"); //will be true if the hive is not private
            boolean notJoined = !s.getUserHives().contains(hive.getInt("hiveId")); //will be true if the user is not a member of this hive
            if( viewable && notJoined ){
                s.addToDisplayedHiveList(hive);
                Log.i("other hives: ", " " + hive.getInt("hiveId"));
            }
        }
        s.notifyAdapterChange();
    s.addMarkers();
    }


}
