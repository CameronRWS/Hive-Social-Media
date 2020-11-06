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
     *
     * @param response
     * @param hiveName
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
    public void clearAdapterData() {
        hiveView.clearData();
    }
    public void onPageResume() {
        server.pageResumeRequests();
    }
    public void notifyDataSetChanged() {
        hiveView.notifyDataChange();
    }
    public void likePostLogic(int postId){
        server.checkLikes(postId);
    }

    @Override
    public int getUserId() {return hiveView.getUserId();}
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

    public void onError(VolleyError error){
        Log.i("volleyAppError", "Error: " + error.getMessage());
        Log.i("volleyAppError", "VolleyError: " + error);
    }

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
