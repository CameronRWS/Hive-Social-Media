package com.example.hivefrontend.Hive.Logic;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.hivefrontend.Hive.IHiveView;
import com.example.hivefrontend.Hive.Network.IHiveServerRequest;
import com.example.hivefrontend.SharedPrefManager;
import com.example.hivefrontend.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * HiveLogic handles the logic behind the Hive class and handles the server
 *     responses.
 */

public class HiveLogic implements IHiveVolleyListener {
    IHiveView hiveView;
    IHiveServerRequest server;

    /**
     * The HiveLogic constructor takes interfaces IHiveView and IHiveServerRequest
     * and assigns them to local variables. Then, it adds this volley listener to
     * the server.
     * @param hv interface IHiveView to be used by this HiveLogic
     * @param hsr interface IHiveServerRequest to be used by this HiveLogic
     */
    public HiveLogic(IHiveView hv, IHiveServerRequest hsr) {
        this.hiveView = hv;
        this.server = hsr;
        server.addVolleyListener(this);
    }

    /**
     * setUserHives() calls the IHiveServerRequest server to set the user's hives.
     */
    public void setUserHives() { server.setUserHiveRequest(hiveView.getUserId());}

    /**
     * Gets the Hive Activity's context
     * @return the hive activity's context
     */
    @Override
    public Context getHiveContext() {
        return hiveView.getHiveContext();
    }

    /**
     * Get's the hive's respective description and member count
     * @param response JSONArray response containing member information
     * @param hiveName The string literal which contains the hive's display name
     */
    @Override
    public void onGetHiveNameSuccess(JSONArray response, String hiveName) {
        try {
            for(int i = 0; i < response.length(); i++) {
                JSONObject member = response.getJSONObject(i);
                if (member.getString("name").equals(hiveName)) {
                    hiveView.displayBio(member.getString("description"));
                    server.fetchMemberCount(hiveName);
                    // join status
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * clearAdapterData() clears the data from the adapter class
     */
    public void clearAdapterData() {
        hiveView.clearData();
    }

    /**
     * onPageResume() handles a resume instance by calling the server
     */
    public void onPageResume() {
        server.pageResumeRequests();
    }

    /**
     * Calls the activity once a change in data is detected.
     */
    public void notifyDataSetChanged() {
        hiveView.notifyDataChange();
    }

    /**
     * Invokes the server by checking the amount of likes on a single post in a hive.
     * @param postId The corresponding post's id for reference
     */
    public void likePostLogic(int postId){
        server.checkLikes(postId);
    }

    /**
     * Fetches the current user's id.
     * @return the id
     */
    @Override
    public int getUserId() {return hiveView.getUserId();}

    /**
     * Fetches the hive's display name.
     * @param response JSONArray response which holds member data
     */
    public void onHiveRequestSuccess(JSONArray response){
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject member = response.getJSONObject(i); //should return user,hive pair
                Integer hiveId = (Integer) member.getJSONObject("hive").getInt("hiveId");
                hiveView.addToHiveIdsHome(hiveId);
                String hiveName = member.getJSONObject("hive").getString("name");
                hiveView.addToHiveOptionsHome(hiveName);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("jsonAppError", e.toString());
        }
    }

    /**
     * Logs error messages on an error
     * @param error Received error
     */
    public void onError(VolleyError error){
        Log.i("volleyAppError", "Error: " + error.getMessage());
        Log.i("volleyAppError", "VolleyError: " + error);
    }

    /**
     * Fetches the member count
     * @param response The JSONArray which holds member data
     * @param hiveName The string literal which contains the hive's display name.
     */
    @Override
    public void onFetchMemberCountSuccess(JSONArray response, String hiveName) {
        try {
            int memberCount = 0;
            Log.i("yjha", "here we are");
            for(int i = 0; i < response.length(); i++) {
                JSONObject member = response.getJSONObject(i);
                if (member.getJSONObject("hive").getString("name").equals(hiveName)) {
                    memberCount++;
                    Log.i("yjha", "current num" + memberCount);
                }
            }
            hiveView.displayMemberCount(memberCount);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
