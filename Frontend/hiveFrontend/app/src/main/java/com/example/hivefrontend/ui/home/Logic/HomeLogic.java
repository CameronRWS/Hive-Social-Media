package com.example.hivefrontend.ui.home.Logic;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hivefrontend.ui.home.HomeFragment;
import com.example.hivefrontend.ui.home.Network.ServerRequest;
import com.example.hivefrontend.ui.home.PostComparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class HomeLogic {

    HomeFragment home;
    public HomeLogic(HomeFragment homeFragment) {
        home = homeFragment;
    }

    public void setUserHives() {
        ServerRequest server = new ServerRequest(this);
        server.setUserHiveRequest();
    }

    public void onPageResume() {
        ServerRequest server = new ServerRequest(this);
        server.pageResumeRequests();
    }

    public void updatePostLogic() {
        ServerRequest server = new ServerRequest(this);
        server.updatePostRequest();
    }

    //gets the info about this post to see if the user has already liked it
    public void likePostLogic(int postId){
        ServerRequest server = new ServerRequest(this);
        server.checkLikes(postId);
    }

    public void onHiveRequestSuccess(JSONArray response){
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject member = response.getJSONObject(i); //should return user,hive pair
                Integer hiveId = (Integer) member.getJSONObject("hive").getInt("hiveId");
                home.hiveIdsHome.add(hiveId);
                String hiveName = member.getJSONObject("hive").getString("name");
                home.hiveOptionsHome.add(hiveName);
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

    public Context getHomeContext(){
        return home.getContext();
    }

    public void notifyDataSetChanged() {
        home.homeAdapter.notifyDataSetChanged();
        home.discoverAdapter.notifyDataSetChanged();
    }

    public void clearAdapterData() {
        home.discoverPostObjects.clear();
        home.homePostObjects.clear();
        home.hiveIdsDiscover.clear();
        home.hiveOptionsDiscover.clear();
    }

    public  ArrayList<Integer> getHiveIdsHome(){
        return home.hiveIdsHome;
    }
    public ArrayList<String> getHiveOptionsHome(){
        return home.hiveOptionsHome;
    }
    public ArrayList<Integer> getHiveIdsDiscover(){
        return home.hiveIdsDiscover;
    }
    public ArrayList<String> getHiveOptionsDiscover(){
        return home.hiveOptionsDiscover;
    }

    public void addToDiscoverIds(int hiveId){
        home.hiveIdsDiscover.add(hiveId);
    }

    public void addToDiscoverPosts(JSONObject post){
        home.discoverPostObjects.add(post);
    }

    public void sortData() {
        Collections.sort(home.homePostObjects, new PostComparator());
        Collections.sort(home.discoverPostObjects, new PostComparator());
    }

    public void addToDiscoverOptions(String name) {
        home.hiveOptionsDiscover.add(name);
    }

    public void addToHomePosts(JSONObject post) {
        home.homePostObjects.add(post);
    }
}
