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

/**
 * Interface implemented by the HomeLogic.
 */
public interface IHomeVolleyListener {
    void setUserHives();

    void onPageResume();

    void updatePostLogic();
    //gets the info about this post to see if the user has already liked it
    void likePostLogic(int postId);

    void onHiveRequestSuccess(JSONArray response);

    void onError(VolleyError error);

    Context getHomeContext();

    void notifyDataSetChanged();

    void clearAdapterData();

    void addToHiveIdsHome(int hiveId);

    ArrayList<Integer> getHiveIdsHome();

    ArrayList<String> getHiveOptionsHome();

    ArrayList<Integer> getHiveIdsDiscover();


    ArrayList<String> getHiveOptionsDiscover();

    void addToDiscoverIds(int hiveId);

    void addToDiscoverPosts(JSONObject post);

    void sortData();

    void addToDiscoverOptions(String name);

    void addToHomePosts(JSONObject post);

    int getUserId();
}
