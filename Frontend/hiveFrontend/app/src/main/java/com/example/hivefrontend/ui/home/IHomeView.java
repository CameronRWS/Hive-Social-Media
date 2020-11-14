package com.example.hivefrontend.ui.home;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Interface implemented by the HomeFragment
 */
public interface IHomeView {

    void addToHiveIdsHome(int hiveId);
    void addToHiveOptionsHome(String hiveName);

    void addToHiveIdsDiscover(int hiveId);

    void addToHiveOptionsDiscover(String hiveName);

    void notifyDataChange();

    Context getHomeContext();

    int getUserId();

    void clearData();

    void sortPosts();

    void addToDiscoverPosts(JSONObject post);

    void addToHomePosts(JSONObject post);

    void openHivePage(String str);

    ArrayList<Integer> getHiveIdsHome();

    ArrayList<String> getHiveOptionsHome();

    ArrayList<Integer> getHiveIdsDiscover();

    ArrayList<String> getHiveOptionsDiscover();
}
