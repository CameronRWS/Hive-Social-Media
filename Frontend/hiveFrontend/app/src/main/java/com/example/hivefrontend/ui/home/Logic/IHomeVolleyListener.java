package com.example.hivefrontend.ui.home.Logic;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.hivefrontend.ui.home.Network.ServerRequest;
import com.example.hivefrontend.ui.home.PostComparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public interface IHomeVolleyListener {
    public void setUserHives();

    public void onPageResume();

    public void updatePostLogic();
    //gets the info about this post to see if the user has already liked it
    public void likePostLogic(int postId);

    public void onHiveRequestSuccess(JSONArray response);

    public void onError(VolleyError error);

    public Context getHomeContext();

    public void notifyDataSetChanged();

    public void clearAdapterData();

    public void addToHiveIdsHome(int hiveId);

    public ArrayList<Integer> getHiveIdsHome();

    public ArrayList<String> getHiveOptionsHome();

    public ArrayList<Integer> getHiveIdsDiscover();


    public ArrayList<String> getHiveOptionsDiscover();

    public void addToDiscoverIds(int hiveId);

    public void addToDiscoverPosts(JSONObject post);

    public void sortData();

    public void addToDiscoverOptions(String name);

    public void addToHomePosts(JSONObject post);

    int getUserId();
}
