package com.example.hivefrontend.ui.home.Logic;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hivefrontend.ui.home.HomeFragment;
import com.example.hivefrontend.ui.home.IHomeView;
import com.example.hivefrontend.ui.home.Network.IServerRequest;
import com.example.hivefrontend.ui.home.Network.ServerRequest;
import com.example.hivefrontend.ui.home.PostComparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Class to handle the logic for the HomeFragment, such as handling responses from the server.
 */
public class HomeLogic implements IHomeVolleyListener{

    IHomeView home;
    IServerRequest server;
    public HomeLogic(IHomeView homeFragment, IServerRequest req) {
        home = homeFragment;
        server = req;
        server.addVolleyListener(this);
    }

    /**
     * Makes a call to the ServerRequest class to get the hives this user is a part of.
     */
    public void setUserHives() {
        server.setUserHiveRequest(home.getUserId());
    }

    /**
     * Called when the screen resumes.
     */
    public void onPageResume() {
        server.pageResumeRequests();
    }

    /**
     * Calls the server method to update post information. Called after interactions that necessitate an update in data, such as liking a post.
     */
    public void updatePostLogic() {
        server.updatePostRequest();
    }

    /**
     * Calls the ServerRequest class method to check if this user has liked this post already.
     * @param postId The id of the post the user wants to like.
     */
    public void likePostLogic(int postId){
        server.checkLikes(postId);
    }

    /**
     * Called after a successful server request for this user's hive information. Adds the hive ids and hive names to hiveIds and hiveOptions in the HomeFragment.
     * @param response The response back from server, containing memberships of this user.
     */
    public void onHiveRequestSuccess(JSONArray response){
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject member = response.getJSONObject(i); //should return user,hive pair
                Integer hiveId = (Integer) member.getJSONObject("hive").getInt("hiveId");
                home.addToHiveIdsHome(hiveId);
                String hiveName = member.getJSONObject("hive").getString("name");
                home.addToHiveOptionsHome(hiveName);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("jsonAppError", e.toString());
        }
    }

    /**
     * Called after an error is received during a server request
     * @param error Error received from request
     */
    public void onError(VolleyError error){
        Log.i("volleyAppError", "Error: " + error.getMessage());
        Log.i("volleyAppError", "VolleyError: " + error);
    }

    /**
     * Gets the context of the HomeFragment by calling the getHomeContext method of IHomeView
     * @return Context of the HomeFragment
     */
    public Context getHomeContext(){
        return home.getHomeContext();
    }

    /**
     * Notifies the adapters that the data has changed by calling notifyDataChange method in IHomeView
     */
    public void notifyDataSetChanged() {
        home.notifyDataChange();
    }

    /**
     * Clears the adapter's data by calling clearData method in IHomeView
     */
    public void clearAdapterData() {
        home.clearData();
    }

    /**
     * Gets the hive ids for the home tab
     * @return Hive ids for the home tab
     */
    public  ArrayList<Integer> getHiveIdsHome(){
        return home.getHiveIdsHome();
    }
    /**
     * Gets the hive names for the home tab
     * @return Hive names for the home tab
     */
    public ArrayList<String> getHiveOptionsHome(){
        return home.getHiveOptionsHome();
    }
    /**
     * Gets the hive ids for the discover tab
     * @return Hive ids for the discover tab
     */
    public ArrayList<Integer> getHiveIdsDiscover(){
        return home.getHiveIdsDiscover();
    }
    /**
     * Gets the hive names for the discover tab
     * @return Hive names for the discover tab
     */
    public ArrayList<String> getHiveOptionsDiscover(){
        //return home.hiveOptionsDiscover;
        return home.getHiveOptionsDiscover();

    }

    /**
     * Adds to the list of hive ids for the discover tab
     * @param hiveId Hive id to add
     */
    public void addToDiscoverIds(int hiveId){
        home.addToHiveIdsDiscover(hiveId);
    }
    /**
     * Adds to the list of hive ids for the home tab
     * @param hiveId Hive id to add
     */
    public void addToHiveIdsHome(int hiveId)
    {
        home.addToHiveIdsHome(hiveId);
    }

    /**
     * Adds to the list of posts for the discover tab
     * @param post Post to add
     */
    public void addToDiscoverPosts(JSONObject post){
        home.addToDiscoverPosts(post);
    }

    /**
     * Sorts the posts in the home and discover tab by calling the sortPosts method of IHomeView
     */
    public void sortData() {
        home.sortPosts();
    }

    /**
     * Adds to the list of hive names for the discover tab
     * @param name Hive name to add
     */
    public void addToDiscoverOptions(String name) {
        home.addToHiveOptionsDiscover(name);
    }

    /**
     * Adds to the list of posts for the home tab
     * @param post Post to add
     */
    public void addToHomePosts(JSONObject post) {
        home.addToHomePosts(post);
    }

    /**
     * Gets the user id for the logged in user
     * @return The current user's user id
     */
    @Override
    public int getUserId() {
        return home.getUserId();
    }
}
