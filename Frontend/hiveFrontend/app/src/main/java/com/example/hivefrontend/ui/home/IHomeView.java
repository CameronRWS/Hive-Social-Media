package com.example.hivefrontend.ui.home;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;

public interface IHomeView {

    public void addToHiveIdsHome(int hiveId);
    public void addToHiveOptionsHome(String hiveName);

    public void addToHiveIdsDiscover(int hiveId);

    public void addToHiveOptionsDiscover(String hiveName);

    public void notifyDataChange();

    public Context getHomeContext();

    void clearData();

    void sortPosts();

    void addToDiscoverPosts(JSONObject post);

    void addToHomePosts(JSONObject post);

    ArrayList<Integer> getHiveIdsHome();

    ArrayList<String> getHiveOptionsHome();

    ArrayList<Integer> getHiveIdsDiscover();

    ArrayList<String> getHiveOptionsDiscover();
}
